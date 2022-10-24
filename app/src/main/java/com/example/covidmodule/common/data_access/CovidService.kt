package com.example.covidmodule.common.data_access

import com.example.covidmodule.common.entities.CovidDataEntity
import com.example.covidmodule.common.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface CovidService {

    @GET(Constants.API_PATH)
    suspend fun getCovidDataFromDate(
        @Query(Constants.DATE_PARAM) date: String,
    ): CovidDataEntity
}