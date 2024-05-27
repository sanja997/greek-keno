package com.sanjacurcic.ui.gameresults.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sanjacurcic.domain.model.GameResultUiModel
import com.sanjacurcic.ui.databinding.ItemGameResultLayoutBinding
import com.sanjacurcic.ui.gameresults.view.GameResultsViewHolder

class GameResultsAdapter : ListAdapter<GameResultUiModel, GameResultsViewHolder>(
    AsyncDifferConfig.Builder(GameResultsDiffUtil()).build()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameResultsViewHolder {
        return GameResultsViewHolder(
            ItemGameResultLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: GameResultsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private class GameResultsDiffUtil : DiffUtil.ItemCallback<GameResultUiModel>() {
        override fun areItemsTheSame(oldItem: GameResultUiModel, newItem: GameResultUiModel): Boolean {
            return oldItem.drawId == newItem.drawId
        }

        override fun areContentsTheSame(oldItem: GameResultUiModel, newItem: GameResultUiModel): Boolean {
            return oldItem == newItem
        }
    }
}