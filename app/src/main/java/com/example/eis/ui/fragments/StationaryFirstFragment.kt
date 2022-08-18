package com.example.eis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import com.example.eis.R
import com.example.eis.databinding.FragmentStationaryFirstBinding
import com.example.eis.ui.base.BaseFragment

class StationaryFirstFragment: BaseFragment() {

    private lateinit var rootView: View
    private lateinit var spinnerProvince: Spinner
    private lateinit var spinnerCity: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentStationaryFirstBinding>(
            inflater,
            R.layout.fragment_stationary_first,
            container,
            false)

        rootView = binding.root

        spinnerProvince = rootView.findViewById(R.id.spinner_areaProvince)
        spinnerCity = rootView.findViewById(R.id.spinner_areaCity)

        checkProvince()
        return rootView
    }
    fun checkProvince(): Spinner {

        spinnerProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var resProvince = parent?.getItemAtPosition(position).toString()
                var arrayAdapter: ArrayAdapter<String>

                if(resProvince == "BUKIDNON"){
                    var list = (resources.getStringArray(R.array.cityBukidnon) as Array<*>).map { it.toString() }

                    arrayAdapter = ArrayAdapter(rootView.context,android.R.layout.simple_spinner_item, list)
                    spinnerCity.adapter = arrayAdapter
                }
                if(resProvince == "CAMIGUIN"){
                    var list = (resources.getStringArray(R.array.cityCamiguin) as Array<*>).map { it.toString() }

                    arrayAdapter = ArrayAdapter(rootView.context,android.R.layout.simple_spinner_item, list)
                    spinnerCity.adapter = arrayAdapter
                }
                if(resProvince == "LANAO DEL NORTE"){
                    var list = (resources.getStringArray(R.array.cityLanao) as Array<*>).map { it.toString() }

                    arrayAdapter = ArrayAdapter(rootView.context,android.R.layout.simple_spinner_item, list)
                    spinnerCity.adapter = arrayAdapter
                }
                if(resProvince == "MISAMIS OCCIDENTAL"){
                    var list = (resources.getStringArray(R.array.cityOccidental) as Array<*>).map { it.toString() }

                    arrayAdapter = ArrayAdapter(rootView.context,android.R.layout.simple_spinner_item, list)
                    spinnerCity.adapter = arrayAdapter
                }
                if(resProvince == "MISAMIS ORIENTAL"){
                    var list = (resources.getStringArray(R.array.cityOriental) as Array<*>).map { it.toString() }

                    arrayAdapter = ArrayAdapter(rootView.context,android.R.layout.simple_spinner_item, list)
                    spinnerCity.adapter = arrayAdapter
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        return spinnerProvince

    }
}