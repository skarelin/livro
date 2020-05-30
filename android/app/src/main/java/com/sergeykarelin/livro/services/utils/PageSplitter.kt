package com.sergeykarelin.livro.services.utils

import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.StaticLayout
import android.text.TextPaint

class PageSplitter(private val pageWidth: Int, private val pageHeight: Int, private val lineSpacingMultiplier: Float, private val lineSpacingExtra: Float) {

    private val pages = mutableListOf<CharSequence>()
    private val mSpannableStringBuilder = SpannableStringBuilder()

    fun append(charSequence: CharSequence?) {
        mSpannableStringBuilder.append(charSequence)
    }

    fun split(textPaint: TextPaint?) {
        val staticLayout = StaticLayout(
                mSpannableStringBuilder,
                textPaint,
                pageWidth,
                Layout.Alignment.ALIGN_NORMAL, lineSpacingMultiplier,
                lineSpacingExtra,
                false
        )
        var startLine = 0
        while (startLine < staticLayout.lineCount) {
            val startLineTop = staticLayout.getLineTop(startLine)
            val endLine = staticLayout.getLineForVertical(startLineTop + pageHeight)
            val endLineBottom = staticLayout.getLineBottom(endLine)
            var lastFullyVisibleLine: Int
            lastFullyVisibleLine = if (endLineBottom > startLineTop + pageHeight) endLine - 1 else endLine
            val startOffset = staticLayout.getLineStart(startLine)
            val endOffset = staticLayout.getLineEnd(lastFullyVisibleLine)
            pages.add(mSpannableStringBuilder.subSequence(startOffset, endOffset))
            startLine = lastFullyVisibleLine + 1
        }
    }

    fun getPages() = pages

}