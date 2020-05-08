package com.android.countries.di

import com.android.countries.model.CountriesService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service:CountriesService)
}