package com.sanjacurcic.ui.gameresults.view

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.chip.Chip
import com.sanjacurcic.domain.helper.getFormattedDate
import com.sanjacurcic.domain.helper.getFormattedTime
import com.sanjacurcic.domain.model.GameResultUiModel
import com.sanjacurcic.ui.R
import com.sanjacurcic.ui.databinding.ItemGameResultLayoutBinding
import java.time.Instant

class GameResultsViewHolder(private val binding: ItemGameResultLayoutBinding) :
    ViewHolder(binding.root) {

    companion object {
        const val TIME_PATTERN = "hh:mm"
        const val DATE_PATTERN = "dd.MM"
    }

    fun bind(model: GameResultUiModel) {
        val instantVale = Instant.ofEpochMilli(model.drawTime)
        val date = instantVale.getFormattedDate(DATE_PATTERN)
        val time = instantVale.getFormattedTime(TIME_PATTERN)

        binding.drawTimeText.text = binding.root.context.getString(
            R.string.draw_time_with_time, "$date | $time"
        )

        binding.roundText.text = binding.root.context.getString(
            R.string.round_with_round_id, model.drawId.toString()
        )

        model.winningNumbers.forEach {

            val chip = Chip(binding.root.context)
            chip.text = it.toString()
            chip.setChipBackgroundColorResource(R.color.ziggurat_semi_transparent)
            binding.winningNumbersGroup.addView(chip)
        }
    }
}