package com.android.countries.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.countries.di.DaggerApiComponent
import com.android.countries.model.CountriesService
import com.android.countries.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountryViewModel : ViewModel() {

    @Inject
    lateinit var countriesService :CountriesService
    private val disposable = CompositeDisposable()

    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    init {
        // injected CountrisService dependency in this(CountryViewModel) class
        DaggerApiComponent.create().inject(this)
    }

    fun refresh() {
        fetchCountries()
    }

    /*
    *  function which will fetch countries from backend using Retrofit and RxJava
    * */
    private fun fetchCountries() {

        loading.value = true

        disposable.add(
            countriesService.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(value: List<Country>) {
                        countries.value = value
                        loading.value = false
                        countryLoadError.value = false
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        countryLoadError.value = true
                    }

                })
        )
    }

    /**
     *  disposable object cleared
     */
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}