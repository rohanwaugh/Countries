package com.android.countries.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.countries.R
import com.android.countries.model.Country
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(var countries:ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    fun updateCountries(newCountries: List<Country>){
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CountryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_country,parent,false)
    )

    override fun getItemCount(): Int  = countries.count()

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }


    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val countryName = itemView.countryName
        fun bind(country: Country){
            countryName.text = country.countryName
        }
    }
}