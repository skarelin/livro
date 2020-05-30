package com.sergeykarelin.livro.services.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.sergeykarelin.livro.R

object DesignUtils {

    @TargetApi(23)
    fun setLightStatusBar(view: View) {
        var flags = view.systemUiVisibility
        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        view.systemUiVisibility = flags
    }

    fun tintNavigationBar(activity: Activity, @ColorInt color: Int) {
        activity.window.navigationBarColor = color
    }

    @TargetApi(26)
    fun setLightNavigationBarIcons(view: View) {
        var flags = view.systemUiVisibility
        flags = flags or WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        view.systemUiVisibility = flags
    }

    fun tintStatusBar(activity: Activity, @ColorRes color: Int) {
        activity.window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = getColor(activity, color)
        }
    }

    @ColorInt
    fun resolveColorAttribute(context: Context, @AttrRes colorAttr: Int): Int {
        val resolvedAttr = resolveThemeAttribute(context, colorAttr)
        val colorRes = if (resolvedAttr.resourceId != 0) resolvedAttr.resourceId else resolvedAttr.data
        return ContextCompat.getColor(context, colorRes)
    }

    @ColorInt
    fun getColor(context: Context, @ColorRes colorRes: Int): Int {
        return ContextCompat.getColor(context, colorRes)
    }

    @ColorInt
    fun resolveAccentColor(context: Context): Int {
        val typedValue = TypedValue()
        val typedArray = context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorAccent))
        val color = typedArray.getColor(0, 0)
        typedArray.recycle()

        return color
    }

    private fun resolveThemeAttribute(context: Context, @AttrRes attrRes: Int): TypedValue {
        val theme = context.theme
        val typedValue = TypedValue()
        theme.resolveAttribute(attrRes, typedValue, true)
        return typedValue
    }

}