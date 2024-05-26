package com.sanjacurcic.ui.game.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sanjacurcic.ui.databinding.ItemOddLayoutBinding
import com.sanjacurcic.ui.game.model.OddsUiModel
import com.sanjacurcic.ui.game.view.OddsViewHolder

class OddsAdapter : ListAdapter<OddsUiModel, OddsViewHolder>(
    AsyncDifferConfig.Builder(OddsDiffUtil()).build()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OddsViewHolder {
        return OddsViewHolder(
            ItemOddLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: OddsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private class OddsDiffUtil : DiffUtil.ItemCallback<OddsUiModel>() {
        override fun areItemsTheSame(oldItem: OddsUiModel, newItem: OddsUiModel): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: OddsUiModel, newItem: OddsUiModel): Boolean {
            return oldItem == newItem
        }
    }
}