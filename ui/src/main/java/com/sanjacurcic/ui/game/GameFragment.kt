package com.sanjacurcic.ui.game

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.sanjacurcic.ui.R
import com.sanjacurcic.ui.databinding.FragmentGameBinding
import com.sanjacurcic.ui.game.adapter.OddsAdapter
import com.sanjacurcic.ui.game.view.NumberView
import com.sanjacurcic.ui.helper.getMinutesAndSecondsString
import com.sanjacurcic.ui.mock.OddMockData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameFragment : Fragment() {

    companion object {
        const val MAX_SELECTED_NUMBERS = 15
    }

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by viewModels()
    private val args: GameFragmentArgs by navArgs()

    private lateinit var oddsAdapter: OddsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.isGameEnded) findNavController().popBackStack()
        viewModel.getGameInfoData(args.drawId)

        setUpViews()
        bindFromViewModel()
        setUpListeners()
    }

    private fun setUpViews() {
        val formattedTime = viewModel.convertEpochMilliToFormattedTime(args.drawTime)
        binding.drawTimeText.text = requireContext().getString(R.string.draw_time_with_time, formattedTime)
        binding.roundText.text = requireContext().getString(R.string.round_with_round_id, args.drawId.toString())
        binding.pickedNumbersText.text = requireContext().getString(R.string.sum_of_picked_numbers, viewModel.pickedNumbers.size)
        binding.pickedNumbersText.isVisible = viewModel.pickedNumbers.isNotEmpty()
        viewModel.pickedNumbers.forEach { binding.pickedNumbersView.addNumber(it) }
        binding.numbersTable.setUpTable(viewModel.pickedNumbers)
        setUpCounter()
    }

    private fun setUpCounter() {

        val timeToPay = viewModel.getTimeToPay(args.drawTime)

        object : CountDownTimer(timeToPay, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val secondsUntilFinished = millisUntilFinished / 1000
                binding.timeToPayText.text = secondsUntilFinished.getMinutesAndSecondsString()
            }

            override fun onFinish() {
                viewModel.isGameEnded = true
                findNavController().navigate(GameFragmentDirections.fragmentGameToWebView())
                this.cancel()
            }
        }.start()
    }

    private fun setUpListeners() {
        binding.numbersTable.onNumberPick = { number, numberView ->
           handleOnNumberPick(number, numberView)
        }

        binding.pickNumberButton.setOnClickListener {
            binding.numbersTable.pickRandomNumber(viewModel.pickedNumbers)
        }
    }

    private fun handleOnNumberPick(number: Int, numberView: NumberView) {

        if (!viewModel.isNumberAlreadyPicked(number)) {
            if (viewModel.pickedNumbers.size < MAX_SELECTED_NUMBERS) {
                binding.pickedNumbersView.addNumber(number)
                viewModel.pickedNumbers.add(number)
                numberView.pickNumber(true)
            }
        } else {
            val position = viewModel.pickedNumbers.indexOf(number)
            binding.pickedNumbersView.removeNumber(position)
            viewModel.pickedNumbers.remove(number)
            numberView.pickNumber(false)
        }
        binding.pickedNumbersText.text = requireContext().getString(R.string.sum_of_picked_numbers, viewModel.pickedNumbers.size)
        binding.pickedNumbersText.isVisible = viewModel.pickedNumbers.isNotEmpty()
    }

    private fun bindFromViewModel() {
        lifecycleScope.launch {
            viewModel.state.collectLatest {
                setUpAdapter()
            }
        }
    }


    private fun setUpAdapter() {


        oddsAdapter = OddsAdapter()

        val horizontalLayoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
        binding.oddRv.setLayoutManager(horizontalLayoutManager)
        binding.oddRv.adapter = oddsAdapter
        oddsAdapter.submitList(OddMockData.oddList)
    }
}