package com.example.covidmodule.main_module.model

import com.example.covidmodule.common.entities.CovidDataEntity
import kotlinx.coroutines.flow.Flow

class CovidModuleRepository {

    fun getCovidDataFromDate(date: String): Flow<CovidDataEntity> =
        RemoteApi.getCovidDataFromDate(date)
}