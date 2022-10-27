package com.example.covidmodule.viewmodel

import com.example.covidmodule.common.entities.CovidDataEntity
import com.example.covidmodule.common.entities.CovidResultData

class CovidEntityFactory {
    fun getCorrectCovidData(): CovidDataEntity{
        return CovidDataEntity(data= CovidResultData(deaths = 10000, confirmed = 5000))
    }

}