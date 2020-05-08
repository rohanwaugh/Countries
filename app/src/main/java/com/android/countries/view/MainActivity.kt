package com.android.countries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.countries.R
import com.android.countries.viewModel.CountryViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : CountryViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.refresh()

        countryRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countryAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel(){

        viewModel.countries.observe(this, Observer {countries->
            countries.let {
                countryRecyclerview.visibility = View.VISIBLE
                countryAdapter.updateCountries(it)
            }
        })

        viewModel.countryLoadError.observe(this, Observer {isError->
            isError?.let {
                errorText.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer {isLoading->
            isLoading?.let {
                loader.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    errorText.visibility = View.GONE
                    countryRecyclerview.visibility = View.GONE
                }
            }
        })
    }
}
