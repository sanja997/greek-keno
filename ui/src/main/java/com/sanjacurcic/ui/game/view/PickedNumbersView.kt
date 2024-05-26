package com.sanjacurcic.ui.game.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.sanjacurcic.ui.databinding.ItemPickedNumbersBinding

class PickedNumbersView(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    fun addNumber(number: Int) {
        val view = ItemPickedNumbersBinding.inflate(LayoutInflater.from(context))
        view.tableNumberText. text = number.toString()
        this.addView(view.root)
    }

    fun removeNumber(position: Int) {
      this.removeViewAt(position)
    }
}