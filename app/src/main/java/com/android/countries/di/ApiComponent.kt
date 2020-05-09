package com.android.countries.di

import com.android.countries.model.CountriesService
import com.android.countries.viewModel.CountryViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    /* This will inject(provide) countriesAPI dependency using provide function from ApiModule in CountriesService class */
    fun inject(service:CountriesService)

    /* This will inject(provide) countryService dependency using provide function from ApiModule in CountryViewModel class */
    fun inject(viewModel: CountryViewModel)
}