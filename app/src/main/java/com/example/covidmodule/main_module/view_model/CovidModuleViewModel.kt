package com.example.covidmodule.main_module.view_model

import androidx.lifecycle.*
import com.example.covidmodule.R
import com.example.covidmodule.common.utils.CovidModuleViewStates
import com.example.covidmodule.main_module.usecases.GetCovidDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CovidModuleViewModel @Inject constructor(
    private val getCovidDataUseCase: GetCovidDataUseCase
) : ViewModel() {

    private val dataStateFlow: MutableStateFlow<CovidModuleViewStates> =
        MutableStateFlow(CovidModuleViewStates.Loading)
    val stateFlow: StateFlow<CovidModuleViewStates> = dataStateFlow


    fun getCovidDataFromDate(date: String) {
        dataStateFlow.value = CovidModuleViewStates.Loading
        getCovidDataUseCase(date).onEach {
            dataStateFlow.value =
                CovidModuleViewStates.Success(it, date.split("-").reversed().joinToString("-"))

        }.catch {
            if (it.toString().contains("UnknownHostException")) {
                dataStateFlow.value = CovidModuleViewStates.Failure(R.string.unknown_host_error)
            } else {
                dataStateFlow.value = CovidModuleViewStates.Failure(R.string.main_error)
            }
        }.launchIn(viewModelScope)
    }

}