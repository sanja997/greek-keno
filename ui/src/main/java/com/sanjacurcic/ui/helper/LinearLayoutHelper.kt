package com.sanjacurcic.ui.helper

import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams

fun LinearLayout.setLayoutWeight(weight: Float) {
    val params = this.layoutParams as LinearLayout.LayoutParams
    this.updateLayoutParams {
        params.width = LinearLayout.LayoutParams.MATCH_PARENT
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT
        params.weight = weight
    }
}