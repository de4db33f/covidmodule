package com.example.covidmodule.main_module.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.covidmodule.BR
import com.example.covidmodule.R
import com.example.covidmodule.common.utils.CommonUtils
import com.example.covidmodule.common.utils.MainViewState
import com.example.covidmodule.databinding.FragmentMainBinding
import com.example.covidmodule.main_module.view_model.MainViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MainViewFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews() {
        binding.selectDateButton.setOnClickListener {
            val dpd = MaterialDatePicker.Builder.datePicker().build()
            dpd.addOnPositiveButtonClickListener {
                lifecycleScope.launch {
                    //FIXME: Le tuve que sumar 1 día (en milisegundos) porque siempre obtenía el epoch del día anterior al que seleccionaba
                    binding.viewModel?.getCovidDataFromDate(CommonUtils.getFullDate(it + 24 * 60 * 60 * 1000))
                    showState()
                }
            }
            dpd.show(parentFragmentManager, "DatePicker")
        }
    }

    private suspend fun showState() {
        binding.viewModel?.stateFlow?.collect {
            when (it) {
                is MainViewState.Failure -> {
                    Snackbar.make(binding.root, it.msg, Snackbar.LENGTH_LONG).show()
                }
                is MainViewState.Success -> {
                    binding.confirmCases.text =
                        getString(R.string.confirmed_cases, it.result.data.confirmed.toString())
                    binding.numDeaths.text =
                        getString(R.string.death_cases, it.result.data.deaths.toString())

                    binding.date.text = CommonUtils.getDateFormatted(it.date)
                }
                else -> {
                    binding.confirmCases.text = getString(R.string.confirmed_cases, "0")
                    binding.numDeaths.text = getString(R.string.death_cases, "0")

                    binding.date.text = getString(R.string.fatal_error_date)
                }
            }
        }
    }

    private fun setupViewModel() {
        val vm: MainViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -1)
            val current = dateFormat.format(cal.time)
            binding.viewModel?.getCovidDataFromDate(current)
            showState()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainViewFragment()
    }
}