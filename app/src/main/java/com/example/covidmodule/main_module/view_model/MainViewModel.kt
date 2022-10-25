package com.example.covidmodule.main_module.view_model

import androidx.lifecycle.*
import com.example.covidmodule.R
import com.example.covidmodule.common.utils.MainViewState
import com.example.covidmodule.main_module.usecases.GetCovidDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCovidDataUseCase: GetCovidDataUseCase
) : ViewModel() {

    private val dataStateFlow: MutableStateFlow<MainViewState> =
        MutableStateFlow(MainViewState.Loading)
    val stateFlow: StateFlow<MainViewState> = dataStateFlow


    fun getCovidDataFromDate(date: String) {
        dataStateFlow.value = MainViewState.Loading
        getCovidDataUseCase(date).onEach {
            dataStateFlow.value =
                MainViewState.Success(it, date.split("-").reversed().joinToString("-"))

        }.catch {
            if (it.toString().contains("UnknownHostException")) {
                dataStateFlow.value = MainViewState.Failure(R.string.unknown_host_error)
            } else {
                dataStateFlow.value = MainViewState.Failure(R.string.main_error)
            }
        }.launchIn(viewModelScope)
    }


}