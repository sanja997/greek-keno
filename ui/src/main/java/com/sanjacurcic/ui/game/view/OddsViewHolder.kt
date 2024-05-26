package com.sanjacurcic.ui.game.view

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sanjacurcic.ui.databinding.ItemOddLayoutBinding
import com.sanjacurcic.ui.game.model.OddsUiModel

class OddsViewHolder(private val binding: ItemOddLayoutBinding) : ViewHolder(binding.root) {

    fun bind(oddModel: OddsUiModel) {
        binding.numberText.text = oddModel.number.toString()
        binding.oddText.text = oddModel.odd.toString()
    }
}