package com.sanjacurcic.ui.gameresults

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.sanjacurcic.domain.model.GameResultUiModel
import com.sanjacurcic.ui.R
import com.sanjacurcic.ui.databinding.FragmentGameResultsBinding
import com.sanjacurcic.ui.gameresults.adapter.GameResultsAdapter
import com.sanjacurcic.ui.gameresults.model.GameResultsViewState.Error
import com.sanjacurcic.ui.gameresults.model.GameResultsViewState.Loading
import com.sanjacurcic.ui.gameresults.model.GameResultsViewState.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameResultsFragment : Fragment() {

    private var _binding: FragmentGameResultsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameResultsViewModel by viewModels()
    private lateinit var adapter: GameResultsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getGameResults()

        bindFromViewModel()
    }

    private fun bindFromViewModel() {
        viewModel.viewModelScope.launch {
            viewModel.state.collectLatest {
                binding.gameProgressBar.isVisible = it is Loading
                when (it) {
                    is Result -> setUpAdapter(it.result)
                    is Error -> handleErrorState(it.error)
                    else -> {}
                }
            }
        }
    }

    private fun setUpAdapter(list: List<GameResultUiModel>) {
        adapter = GameResultsAdapter()
        binding.gameResultsRv.adapter = adapter
        adapter.submitList(list)
        binding.gameResultsRv.setItemViewCacheSize(list.size)
    }

    private fun handleErrorState(error: Throwable) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.apply {
            setTitle(context.getString(R.string.something_went_wrong))
            setMessage(error.localizedMessage)
            setPositiveButton(context.getString(R.string.try_again)) { _: DialogInterface?, _: Int ->
                viewModel.getGameResults()
            }
        }.create().show()
    }
}