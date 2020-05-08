package com.android.countries.model

import com.android.countries.utilities.BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountriesService {

    private val api: CountriesAPI

    init {
        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // This will convert backend data into JSON data which as per our Data class
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // This will convert the data class into Observable objects similar to LiveData
            .build()
            .create(CountriesAPI::class.java)
    }

    fun getCountries(): Single<List<Country>>{
        return api.getCountries()
    }
}