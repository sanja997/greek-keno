package com.sanjacurcic.ui.gamerounds

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.sanjacurcic.data.model.GameRoundModel
import com.sanjacurcic.ui.R
import com.sanjacurcic.ui.databinding.FragmentGameRoundsBinding
import com.sanjacurcic.ui.gamerounds.adapter.GameRoundsAdapter
import com.sanjacurcic.ui.gamerounds.model.GameRoundsViewState
import com.sanjacurcic.ui.gamerounds.model.GameRoundsViewState.Loading
import com.sanjacurcic.ui.gamerounds.model.GameRoundsViewState.Error
import com.sanjacurcic.ui.gamerounds.model.GameRoundsViewState.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameRoundsFragment : Fragment() {

    private var _binding: FragmentGameRoundsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameRoundsViewModel by viewModels()
    private lateinit var adapter: GameRoundsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameRoundsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRoundsData()
        bindFromViewModel()
    }

    private fun bindFromViewModel() {
        viewModel.viewModelScope.launch {
            viewModel.state.collectLatest { handleState(it) }
        }
    }

    private fun handleState(state: GameRoundsViewState) {
        binding.gameRoundsProgressBar.isVisible = state is Loading
        when (state) {
            is Result -> setUpAdapter(state.result)
            is Error -> handleErrorState(state.error)
            else -> {}
        }
    }

    private fun handleErrorState(error: Throwable) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.apply {
            setTitle(context.getString(R.string.something_went_wrong))
            setMessage(error.localizedMessage)
            setPositiveButton(context.getString(R.string.try_again)) { _: DialogInterface?, _: Int ->
                viewModel.getRoundsData()
            }
        }.create().show()
    }

    private fun setUpAdapter(gameRounds: List<GameRoundModel>) {
        adapter = GameRoundsAdapter(
            onTimerFinished = { handleOnTimerFinished(it) },
            onItemClicked = {
                findNavController().navigate(
                    GameRoundsFragmentDirections.fragmentGameRoundsToFragmentGame(it.drawId, it.drawTime,)
                )
            }
        )

        binding.gameRoundsRv.adapter = adapter
        adapter.submitList(gameRounds)
        binding.gameRoundsRv.setItemViewCacheSize(gameRounds.size)
    }

    private fun handleOnTimerFinished(position: Int) {
        adapter.notifyItemRemoved(position)
        Handler(Looper.getMainLooper()).postDelayed({ viewModel.getRoundsData() }, 700)
    }
}