package com.example.covidmodule.main_module.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.covidmodule.R
import com.example.covidmodule.common.utils.CommonUtils
import com.example.covidmodule.common.utils.CovidModuleViewStates
import com.example.covidmodule.databinding.FragmentMainBinding
import com.example.covidmodule.main_module.view_model.CovidModuleViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CovidModuleViewFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private lateinit var viewModel: CovidModuleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        setupViews()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity())[CovidModuleViewModel::class.java]
    }

    private fun setupViews() {
        binding.selectDateButton.setOnClickListener {
            val dpd = MaterialDatePicker.Builder.datePicker().build()
            dpd.addOnPositiveButtonClickListener {
                lifecycleScope.launch {
                    //FIXME: Le tuve que sumar 1 día (en milisegundos) porque siempre obtenía el epoch del día anterior al que seleccionaba
                    viewModel.getCovidDataFromDate(CommonUtils.getFullDate(it + 24 * 60 * 60 * 1000))
                    showState()
                }
            }
            dpd.show(parentFragmentManager, "DatePicker")
        }
    }

    private suspend fun showState() {
        viewModel.stateFlow.collect {
            when (it) {
                is CovidModuleViewStates.Failure -> {
                    hideProgressBar()
                    Snackbar.make(binding.root, it.msg, Snackbar.LENGTH_LONG).show()
                }
                is CovidModuleViewStates.Success -> {
                    hideProgressBar()
                    binding.confirmCases.text =
                        getString(R.string.confirmed_cases, it.result.data.confirmed.toString())
                    binding.numDeaths.text =
                        getString(R.string.death_cases, it.result.data.deaths.toString())

                    binding.date.text = CommonUtils.getDateFormatted(it.date)
                }
                is CovidModuleViewStates.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.layout.visibility = View.GONE
        binding.progressIndicator.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.layout.visibility = View.VISIBLE
        binding.progressIndicator.visibility = View.GONE
    }


    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -1)
            val current = dateFormat.format(cal.time)
            viewModel.getCovidDataFromDate(current)
            showState()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CovidModuleViewFragment()
    }
}