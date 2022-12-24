package com.binar.finalproject14.ui

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.databinding.FragmentListViewBinding
import com.binar.finalproject14.viewmodel.AirportViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListViewFragment : Fragment() {
    private var _binding: FragmentListViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var airportViewModel: AirportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        airportViewModel = ViewModelProvider(this)[AirportViewModel::class.java]

        val mCities = arguments?.getStringArrayList("mCities")

        if (mCities != null){
            val mArrayAdapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, R.id.text1, mCities)
            binding.listView.adapter = mArrayAdapter

                binding.listView.adapter = mArrayAdapter

                binding.editText.addTextChangedListener(object: TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        // Do Nothing
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        mArrayAdapter.filter.filter(s)
                    }

                    override fun afterTextChanged(s: Editable?) {

                    }

                })
                binding.listView.setClickable(true)
                binding.listView.setOnItemClickListener(AdapterView.OnItemClickListener { arg0, arg1, position, arg3 ->
                    val o: Any = binding.listView.getItemAtPosition(position)
                    val str = o as String //As you are using Default String Adapter
                    var bund = Bundle()
                    bund.putString("departure", str)
                    Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
//                    findNavController().navigate(R.id.action_listViewFragment_to_homeFragment, bund)
                })
        }

        super.onViewCreated(view, savedInstanceState)
    }
}