package com.binar.finalproject14.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
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
        return view
    }

    override fun getFilter() = filter

    inner class AirportFilter(private val originalList: List<DataAirport>) : Filter() {

        private val sourceObjects: ArrayList<DataAirport> = ArrayList()

        init {
            synchronized (this) {
                sourceObjects.addAll(originalList)
            }
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            if (constraint == null) return FilterResults()

            val result = FilterResults()

            if (constraint.isNotEmpty()) {
                val filteredList = ArrayList<DataAirport>()

                sourceObjects.filterTo(filteredList) { isWithinConstraint(it, constraint) }

                result.count = filteredList.size
                result.values = filteredList

            } else {
                synchronized(this) {
                    result.values = sourceObjects
                    result.count = sourceObjects.size
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