package com.sanjacurcic.ui.gamerounds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sanjacurcic.data.model.GameRoundModel
import com.sanjacurcic.ui.databinding.ItemRoundLayoutBinding
import com.sanjacurcic.ui.gamerounds.view.GameRoundsViewHolder

class GameRoundsAdapter(
    private val onTimerFinished: (Int) -> Unit,
    private val onItemClicked: (GameRoundModel) -> Unit
) : ListAdapter<GameRoundModel, GameRoundsViewHolder>(
    AsyncDifferConfig.Builder(GameRoundsDiffUtil()).build()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameRoundsViewHolder {
        return GameRoundsViewHolder(
            ItemRoundLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: GameRoundsViewHolder, position: Int) {
        holder.bind(currentList[position], onTimerFinished, onItemClicked)
    }

    private class GameRoundsDiffUtil : DiffUtil.ItemCallback<GameRoundModel>() {
        override fun areItemsTheSame(oldItem: GameRoundModel, newItem: GameRoundModel): Boolean {
            return oldItem.gameId == newItem.gameId
        }

        override fun areContentsTheSame(oldItem: GameRoundModel, newItem: GameRoundModel): Boolean {
            return oldItem == newItem
        }
    }
}