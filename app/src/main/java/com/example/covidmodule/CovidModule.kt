package com.example.covidmodule

import androidx.fragment.app.Fragment
import com.example.covidmodule.main_module.view.CovidModuleViewFragment

object CovidModule {
    fun covidModuleFragmentHandler(): Fragment = CovidModuleViewFragment.newInstance()
}