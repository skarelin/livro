package com.sergeykarelin.livro.widgets

import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.TextView
import com.sergeykarelin.livro.Constants.EMPTY_STRING
import com.sergeykarelin.livro.services.extensions.clearHighlights
import com.sergeykarelin.livro.services.extensions.highlightText
import java.text.BreakIterator

/**
 * Created by Pavlo Rekun on 02.05.2020.
 */
class ReaderMovementMethod : LinkMovementMethod() {

    private var ongoingLongPressTimer: LongPressTimer? = null

    private lateinit var onTextClickListener: OnTextClickListener

    private var wasLongPressRegistered = false

    fun setOnTextClickListener(onTextClickListener: OnTextClickListener) {
        this.onTextClickListener = onTextClickListener
    }

    override fun onTouchEvent(readerText: TextView, buffer: Spannable, event: MotionEvent): Boolean {
        val position = findClickPosition(readerText, event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (this::onTextClickListener.isInitialized) {
                    val onTimerReachedListener = object : LongPressTimer.OnTimerReachedListener {

                        override fun onTimerReached() {
                            wasLongPressRegistered = true

                            val sentenceLocation = extractTextByPosition(readerText, position, true)
                            dispatchLongClick(readerText, buffer, sentenceLocation)
                        }

                    }

                    registerLongClickCallback(readerText, onTimerReachedListener)
                }

                return false
            }

            MotionEvent.ACTION_UP -> {
                if (!wasLongPressRegistered) {
                    val wordLocation = extractTextByPosition(readerText, position)
                    dispatchClick(readerText, buffer, wordLocation)
                }

                performCleanUp(readerText)

                return false
            }

            MotionEvent.ACTION_CANCEL -> {
                performCleanUp(readerText)
                return false
            }

            else -> return false
        }
    }

    private fun performCleanUp(readerText: TextView) {
        wasLongPressRegistered = false
        removeLongClickCallback(readerText)
    }

    private fun findClickPosition(readerText: TextView, event: MotionEvent): Int {
        var touchX = event.x.toInt()
        var touchY = event.y.toInt()

        // Take into account padding
        touchX -= readerText.totalPaddingLeft
        touchY -= readerText.totalPaddingTop

        // Take into account scrollable text view (not our case for now, but may be needed in future)
        touchX += readerText.scrollX
        touchY += readerText.scrollY

        val layout = readerText.layout
        val touchedLine = layout.getLineForVertical(touchY)

        return layout.getOffsetForHorizontal(touchedLine, touchX.toFloat())
    }

    private fun extractTextByPosition(readerText: TextView, position: Int, isSentence: Boolean = false): Pair<Int, Int> {
        val boundary = if (isSentence) BreakIterator.getSentenceInstance() else BreakIterator.getWordInstance()
        boundary.setText(readerText.text.toString())

        val end = boundary.following(position)
        val start = boundary.previous()

        return Pair(start, end)
    }

    private fun dispatchClick(readerText: TextView, text: Spannable, wordLocation: Pair<Int, Int>) {
        val word = text.substring(wordLocation.first, wordLocation.second)

        if (word.isNotBlank()) {
            readerText.clearHighlights()
            text.highlightText(wordLocation)

            onTextClickListener.onClick(word)
        }
    }

    private fun dispatchLongClick(readerText: TextView, text: Spannable, sentenceLocation: Pair<Int, Int>) {
        val sentence = text.substring(sentenceLocation.first, sentenceLocation.second)

        if (sentence.isNotBlank()) {
            readerText.clearHighlights()
            text.highlightText(sentenceLocation)

            onTextClickListener.onLongClick(sentence)
        }
    }

    private fun registerLongClickCallback(readerText: TextView,
                                          timerReachedListener: LongPressTimer.OnTimerReachedListener) {
        ongoingLongPressTimer = LongPressTimer().apply {
            setOnTimerReachedListener(timerReachedListener)

            readerText.postDelayed(this, ViewConfiguration.getLongPressTimeout().toLong())
        }
    }

    private fun removeLongClickCallback(readerText: TextView) {
        if (ongoingLongPressTimer != null) {
            readerText.removeCallbacks(ongoingLongPressTimer)
            ongoingLongPressTimer = null
        }
    }

    private class LongPressTimer : Runnable {

        private lateinit var onTimerReachedListener: OnTimerReachedListener

        override fun run() {
            onTimerReachedListener.onTimerReached()
        }

        fun setOnTimerReachedListener(listener: OnTimerReachedListener) {
            onTimerReachedListener = listener
        }

        interface OnTimerReachedListener {
            fun onTimerReached()
        }

    }

    interface OnTextClickListener {

        fun onClick(text: String): Boolean

        fun onLongClick(text: String): Boolean

    }
}