package com.android.countries.di

import com.android.countries.model.CountriesAPI
import com.android.countries.model.CountriesService
import com.android.countries.utilities.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    /*
    * provide CountriesAPI dependency
    * */
    @Provides
    fun provideCountriesApi():CountriesAPI{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // This will convert backend data into JSON data which as per our Data class
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // This will convert the data class into Observable objects similar to LiveData
            .build()
            .create(CountriesAPI::class.java)
    }

    /*
    * provide CountriesService dependency
    * */
    @Provides
    fun provideCountriesService() :CountriesService{
        return CountriesService()
    }
}