package com.sanjacurcic.ui.gamerounds.view

import android.graphics.Color
import android.os.CountDownTimer
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sanjacurcic.data.model.GameRoundModel
import com.sanjacurcic.ui.R
import com.sanjacurcic.ui.databinding.ItemRoundLayoutBinding
import com.sanjacurcic.ui.helper.getFormattedTime
import com.sanjacurcic.ui.helper.getMinutesAndSecondsString
import java.time.Duration
import java.time.Instant
import kotlin.math.abs

class GameRoundsViewHolder(private val binding: ItemRoundLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        const val TIME_PATTERN = "hh:mm"
        const val SECONDS_TO_CHANGE_COLOR = 60
    }

    fun bind(item: GameRoundModel, onTimerFinished: ((Int) -> Unit)?, onItemClicked: (GameRoundModel) -> Unit) {
        val instantTime = Instant.ofEpochMilli(item.drawTime)
        binding.drawTimeText.text = instantTime.getFormattedTime(TIME_PATTERN)
        setUpCounter(instantTime, onTimerFinished)
        binding.gameRoundCardView.setOnClickListener {
            onItemClicked.invoke(item)
        }
    }

    private fun setUpCounter(instantTime: Instant, onTimerFinished: ((Int) -> Unit)?) {

        val timeToPay = abs(Duration.between(instantTime, Instant.now()).toMillis())

        object : CountDownTimer(timeToPay, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val secondsUntilFinished = millisUntilFinished / 1000
                binding.timeToPayText.text = secondsUntilFinished.getMinutesAndSecondsString()
                if (secondsUntilFinished < SECONDS_TO_CHANGE_COLOR) {
                    binding.timeToPayText.setTextColor(Color.RED)
                } else {
                    binding.timeToPayText.setTextColor(ContextCompat.getColor(binding.root.context, R.color.rhino))
                }
            }

            override fun onFinish() {
                binding.root.alpha = 0.5F
                if (adapterPosition > -1) onTimerFinished?.invoke(adapterPosition)
            }
        }.start()
    }
}