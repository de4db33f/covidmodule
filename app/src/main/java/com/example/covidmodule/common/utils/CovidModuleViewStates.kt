package com.example.covidmodule.common.utils

import com.example.covidmodule.common.entities.CovidDataEntity

sealed class CovidModuleViewStates{
    object Loading: CovidModuleViewStates()
    class Failure(val msg: Int): CovidModuleViewStates()
    class  Success(val result: CovidDataEntity, val date: String): CovidModuleViewStates()
}
