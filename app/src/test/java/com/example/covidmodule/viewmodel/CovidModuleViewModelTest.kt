package com.example.covidmodule.viewmodel

import com.example.covidmodule.common.entities.CovidDataEntity
import com.example.covidmodule.common.entities.CovidResultData
import com.example.covidmodule.common.utils.CovidModuleViewStates
import com.example.covidmodule.main_module.usecases.GetCovidDataUseCase
import com.example.covidmodule.main_module.view_model.CovidModuleViewModel
import com.example.covidmodule.utils.MainCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class CovidModuleViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var getCovidDataUseCase: GetCovidDataUseCase

    @Mock
    lateinit var covidModuleViewModel: CovidModuleViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        this.covidModuleViewModel = CovidModuleViewModel(this.getCovidDataUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `Loading state works`(){
        val covidDataEntity = CovidDataEntity(data=CovidResultData(deaths = 10000, confirmed = 5000))
        whenever(getCovidDataUseCase.invoke("2022-10-24")).thenReturn(
            flowOf(covidDataEntity)
        )
        Assert.assertEquals(CovidModuleViewStates.Loading, covidModuleViewModel.stateFlow.value)
    }

    @Test
    fun `Success state works`(){
        val covidDataEntity = CovidDataEntity(data=CovidResultData(deaths = 10000, confirmed = 5000))
        whenever(getCovidDataUseCase.invoke("2022-10-24")).thenReturn(
            flowOf(covidDataEntity)
        )
        //verify(covidModuleViewModel.stateFlow).onChanged(refEq(State.Loading()))
        Assert.assertEquals(CovidModuleViewStates.Loading, covidModuleViewModel.stateFlow.value)

        covidModuleViewModel.getCovidDataFromDate("2022-10-24")
        Assert.assertTrue( covidModuleViewModel.stateFlow.value is CovidModuleViewStates.Success)
    }

    @Test
    fun `Success state works2`(){
        val covidDataEntity = CovidDataEntity(data=CovidResultData(deaths = 10000, confirmed = 5000))
        whenever(getCovidDataUseCase.invoke("2022-10-24")).thenReturn(
            flowOf(covidDataEntity)
        )
        //verify(covidModuleViewModel.stateFlow).onChanged(refEq(State.Loading()))
        Assert.assertEquals(CovidModuleViewStates.Loading, covidModuleViewModel.stateFlow.value)

        covidModuleViewModel.getCovidDataFromDate("2022-10-24")
        Assert.assertTrue( covidModuleViewModel.stateFlow.value is CovidModuleViewStates.Success)
    }

    @Test
    fun `Failure state works`(){
        val covidDataEntity = CovidDataEntity(data=CovidResultData(deaths = 10000, confirmed = 5000))
        whenever(getCovidDataUseCase.invoke("2022-10-24")).thenReturn(
            flowOf(covidDataEntity)
        )
        //verify(covidModuleViewModel.stateFlow).onChanged(refEq(State.Loading()))
        Assert.assertEquals(CovidModuleViewStates.Loading, covidModuleViewModel.stateFlow.value)

        covidModuleViewModel.getCovidDataFromDate("0")
        Assert.assertTrue( covidModuleViewModel.stateFlow.value is CovidModuleViewStates.Failure)
    }

}