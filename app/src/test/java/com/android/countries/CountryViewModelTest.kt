package com.android.countries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.countries.model.CountriesService
import com.android.countries.model.Country
import com.android.countries.viewModel.CountryViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class CountryViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var countriesService: CountriesService

    @InjectMocks
    lateinit var countryViewModel: CountryViewModel

    private var testSingle: Single<List<Country>>? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testCountriesSuccess() {

        val india = Country("India", "Delhi", "tiranga")
        val usa = Country("USA", "Washington", "flag")

        val countryList = arrayListOf(india, usa)
        testSingle = Single.just(countryList)

        `when`(countriesService.getCountries()).thenReturn(testSingle)
        countryViewModel.refresh()

        Assert.assertEquals(2, countryViewModel.countries.value?.size)
        Assert.assertEquals(false, countryViewModel.countryLoadError.value)
        Assert.assertEquals(false, countryViewModel.loading.value)
    }

    @Test
    fun testCountriesFailure() {
        testSingle = Single.error(Throwable())

        `when`(countriesService.getCountries()).thenReturn(testSingle)
        countryViewModel.refresh()

        Assert.assertEquals(true, countryViewModel.countryLoadError.value)
        Assert.assertEquals(false, countryViewModel.loading.value)
    }

    @Before
    fun setupRxSchedulers() {
        val immediate = object : Scheduler() {

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor {
                    it.run()
                })
            }

            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

        }

        RxJavaPlugins.setInitIoSchedulerHandler {
            immediate
        }
        RxJavaPlugins.setInitComputationSchedulerHandler {
            immediate
        }
        RxJavaPlugins.setInitNewThreadSchedulerHandler {
            immediate
        }
        RxJavaPlugins.setSingleSchedulerHandler {
            immediate
        }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            immediate
        }
    }

}