package com.android.countries.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.countries.model.Country

class CountryViewModel : ViewModel() {

    private val countries = MutableLiveData<List<Country>>()
    private val countryLoadError = MutableLiveData<Boolean>()
    private val loading = MutableLiveData<Boolean>()


    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        val mockData = listOf(
            Country("CountryA"),
            Country("CountryB"),
            Country("CountryC"),
            Country("CountryD"),
            Country("CountryE"),
            Country("CountryF"),
            Country("CountryG"),
            Country("CountryH")
        )

        countryLoadError.value = false
        loading.value = false
        countries.value = mockData
    }
}