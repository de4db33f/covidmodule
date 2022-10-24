package com.example.covidmodule.common.utils

import com.example.covidmodule.common.entities.CovidDataEntity

sealed class MainViewState{
    object Loading: MainViewState()
    class Failure(val msg: Int): MainViewState()
    class  Success(val result: CovidDataEntity, val date: String): MainViewState()
}
