package com.sanjacurcic.ui.game.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import com.sanjacurcic.ui.helper.setLayoutWeight

class NumbersTableView(context: Context, attributeSet: AttributeSet) : TableLayout(context, attributeSet) {

    var onNumberPick: ((Int, NumberView) -> Unit)? = null

    fun setUpTable(pickedNumbers: List<Int>) {

        var number = 0
        var tableRow = TableRow(context)
        tableRow.layoutDirection = View.LAYOUT_DIRECTION_LTR
        tableRow.weightSum = 10F

        for (i in 1..8) {
            for (j in 1..10) {

                val numberView = NumberView(context)
                numberView.number = ++number

                tableRow.addView(numberView)
                tableRow.children.forEach {
                    (it as? LinearLayout)?.setLayoutWeight(1F)
                }

                if (pickedNumbers.contains(number)) numberView.pickNumber(true)

                numberView.setOnClickListener {
                    onNumberPick?.invoke(numberView.number, numberView)
                }
            }

            this.addView(tableRow)
            tableRow = TableRow(context)
        }
    }

    fun pickRandomNumber(pickedNumbers: List<Int>) {
        var random = getRandomNumber()
        if (pickedNumbers.contains(random)) random = getRandomNumber()
        val numberView = findViewWithNumber(random)
        numberView?.let {
            onNumberPick?.invoke(it.number, numberView)
        }
    }

    private fun findViewWithNumber(number: Int): NumberView? {
        this.children.forEach {row ->
            (row as? TableRow)?.children?.forEach {
                if ((it as? NumberView)?.number == number) return it
            }
        }
        return null
    }

    private fun getRandomNumber() = (1 .. 80).random()
}