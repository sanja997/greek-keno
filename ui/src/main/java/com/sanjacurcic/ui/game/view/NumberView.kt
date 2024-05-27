package com.sanjacurcic.ui.game.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.sanjacurcic.ui.databinding.ItemNumberTableLayoutBinding


class NumberView(context: Context) : LinearLayout(context) {

    val view = ItemNumberTableLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    var number: Int = 0
        set(value) {
            view.tableNumberText.text = value.toString()
            field = value
        }

    fun pickNumber(show: Boolean) {
        view.numberCircle.isVisible = show
    }
}