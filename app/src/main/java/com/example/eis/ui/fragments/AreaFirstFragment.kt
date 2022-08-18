package com.example.eis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.DataBindingUtil
import com.example.eis.R
import com.example.eis.databinding.FragmentAreaFirstBinding
import com.example.eis.databinding.FragmentMobileFirstBinding
import com.example.eis.databinding.FragmentMobileSecondBinding
import com.example.eis.ui.base.BaseFragment

class AreaFirstFragment: BaseFragment() {

    private lateinit var rootView: View
    private lateinit var spinnerProvince: Spinner
    private lateinit var spinnerCity: Spinner


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAreaFirstBinding>(
            inflater,
            R.layout.fragment_area_first,
            container,
            false)

        rootView = binding.root

        spinnerProvince = rootView.findViewById(R.id.spinner_areaProvince)
        spinnerCity = rootView.findViewById(R.id.spinner_areaCity)

        checkProvince()


        return rootView
    }

    fun getValues(){
        getGeneralInformationArea.year = getValue(rootView,R.id.txt_year)
        getGeneralInformationArea.province = getSpinnerValue(rootView, R.id.spinner_areaProvince, SpinnerEnum.FROM_RESOURCE_XML, R.array.mobileProvinceList)
        /*selection of province function*/
        spinnerProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var resProvince = parent?.getItemAtPosition(position).toString()

                if(resProvince == "BUKIDNON"){
                    getGeneralInformationArea.city = getSpinnerValue(rootView, R.id.spinner_areaCity, SpinnerEnum.FROM_RESOURCE_XML, R.array.cityBukidnon)
                }
                if(resProvince == "CAMIGUIN"){
                    getGeneralInformationArea.city = getSpinnerValue(rootView, R.id.spinner_areaCity, SpinnerEnum.FROM_RESOURCE_XML, R.array.cityCamiguin)
                }
                if(resProvince == "LANAO DEL NORTE"){
                    getGeneralInformationArea.city = getSpinnerValue(rootView, R.id.spinner_areaCity, SpinnerEnum.FROM_RESOURCE_XML, R.array.cityLanao)
                }
                if(resProvince == "MISAMIS OCCIDENTAL"){
                    getGeneralInformationArea.city = getSpinnerValue(rootView, R.id.spinner_areaCity, SpinnerEnum.FROM_RESOURCE_XML, R.array.cityOccidental)
                }
                if(resProvince == "MISAMIS ORIENTAL"){
                    getGeneralInformationArea.city = getSpinnerValue(rootView, R.id.spinner_areaCity, SpinnerEnum.FROM_RESOURCE_XML, R.array.cityOriental)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        /*end function*/

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