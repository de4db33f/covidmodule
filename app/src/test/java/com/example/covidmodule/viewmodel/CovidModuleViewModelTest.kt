package com.example.covidmodule.viewmodel

import com.example.covidmodule.common.utils.CovidModuleViewStates
import com.example.covidmodule.main_module.usecases.GetCovidDataUseCase
import com.example.covidmodule.main_module.view_model.CovidModuleViewModel
import com.example.covidmodule.utils.DateFactory
import com.example.covidmodule.utils.MainCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
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

    private val covidEntityFactory: CovidEntityFactory = CovidEntityFactory()

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        this.covidModuleViewModel = CovidModuleViewModel(this.getCovidDataUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Loading state works`(){
        val date: String = DateFactory().getDateToApi()
        val covidDataEntity = covidEntityFactory.getCorrectCovidData()
        whenever(getCovidDataUseCase.invoke(date)).thenReturn(
            flowOf(covidDataEntity)
        )
        Assert.assertEquals(CovidModuleViewStates.Loading, covidModuleViewModel.stateFlow.value)
    }

    @Test
    fun `Success state works`(){
        val date: String = DateFactory().getDateToApi()
        `Loading state works`()

        covidModuleViewModel.getCovidDataFromDate(date)
        Assert.assertTrue(covidModuleViewModel.stateFlow.value is CovidModuleViewStates.Success)
    }

    @Test
    fun `Failure state works`(){
        `Loading state works`()

        covidModuleViewModel.getCovidDataFromDate("0")
        Assert.assertTrue( covidModuleViewModel.stateFlow.value is CovidModuleViewStates.Failure)
    }

    @Test
    fun `Failure state with today date`(){
        val date: String = DateFactory().getTodayDate()

        covidModuleViewModel.getCovidDataFromDate(date)
        Assert.assertTrue( covidModuleViewModel.stateFlow.value is CovidModuleViewStates.Failure)
    }

    @Test
    fun `Failure state with tomorrow date`(){
        val date: String = DateFactory().getTomorrowDate()

        covidModuleViewModel.getCovidDataFromDate(date)
        Assert.assertTrue( covidModuleViewModel.stateFlow.value is CovidModuleViewStates.Failure)
    }

}