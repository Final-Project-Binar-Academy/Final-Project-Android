package com.binar.finalproject14.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.navigation.Navigation
import com.binar.finalproject14.R
import com.binar.finalproject14.data.api.response.airport.DataAirport
import com.binar.finalproject14.databinding.CustomListViewBinding
import java.util.*

class AirportAdapter(context: Context, airports: ArrayList<DataAirport>) :
    ArrayAdapter<DataAirport>(context, 0, airports) {

    private val filter = AirportFilter(airports)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val airport = getItem(position)
        val view = LayoutInflater.from(context).inflate(R.layout.custom_list_view, parent, false)
        val binding = CustomListViewBinding.bind(view)
        binding.txtCity.text = airport?.city
        binding.txtCityCode.text = airport?.cityCode

        view.setOnClickListener{
            val bund = Bundle()
            bund.putString("city", airport?.city)
            bund.putString("cityCode", airport?.cityCode)
            Navigation.findNavController(view).navigate(R.id.action_listViewFragment_to_homeFragment, bund)
        }
        return view
    }

    override fun getFilter() = filter

    inner class AirportFilter(private val originalList: List<DataAirport>) : Filter() {

        private val airport: ArrayList<DataAirport> = ArrayList()

        init {
            synchronized (this) {
                airport.addAll(originalList)
            }
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            if (constraint == null) return FilterResults()

            val result = FilterResults()

            if (constraint.isNotEmpty()) {

                airport.filterTo(airport) { isWithinConstraint(it, constraint) }

                result.count = airport.size
                result.values = airport

            } else {
                synchronized(this) {
                    result.values = airport
                    result.count = airport.size
                }

            }
            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            if (results.values == null) return

            @Suppress("UNCHECKED_CAST")
            val filtered = results.values as ArrayList<DataAirport>

            if (results.count > 0) {
                clear()
                addAll(filtered)
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }


        override fun convertResultToString(resultValue: Any?): CharSequence {
            return (resultValue as DataAirport).city.toString()
        }

        private fun isWithinConstraint(airport: DataAirport, constraint: CharSequence): Boolean {
            return airport.city?.toLowerCase()?.contains(constraint, true)!!
        }

    }
}