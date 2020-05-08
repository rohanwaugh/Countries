package com.android.countries.di

import com.android.countries.model.CountriesService
import com.android.countries.viewModel.CountryViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service:CountriesService)

    fun inject(viewModel: CountryViewModel)
}