package com.example.eis.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.DataBindingUtil
import com.example.eis.R
import com.example.eis.databinding.FragmentStationaryFirstBinding
import com.example.eis.ui.base.BaseFragment
import com.example.eis.ui.utils.PrefsUtil
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class StationaryFirstFragment: BaseFragment() {

    private lateinit var rootView: View
    private lateinit var spinnerProvince: Spinner
    private lateinit var spinnerCity: Spinner
    private lateinit var resCity: String
    private lateinit var resindustry: String
    private lateinit var resindustryOthers: String

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
        resCity = String()
        resindustry = String()
        resindustryOthers= String()
        initFields()

        spinnerProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var resProvince = parent?.getItemAtPosition(position).toString()
                var arrayAdapter: ArrayAdapter<String>
                var list : List<String> = listOf()

                if(resProvince == "BUKIDNON"){
                    list = (resources.getStringArray(R.array.cityBukidnon) as Array<*>).map { it.toString() }
                    arrayAdapter = ArrayAdapter(rootView.context,android.R.layout.simple_spinner_item, list)
                    spinnerCity.adapter = arrayAdapter


                }
                if(resProvince == "CAMIGUIN"){
                    list = (resources.getStringArray(R.array.cityCamiguin) as Array<*>).map { it.toString() }
                    arrayAdapter = ArrayAdapter(rootView.context,android.R.layout.simple_spinner_item, list)
                    spinnerCity.adapter = arrayAdapter


                }
                if(resProvince == "LANAO DEL NORTE"){
                    list = (resources.getStringArray(R.array.cityLanao) as Array<*>).map { it.toString() }
                    arrayAdapter = ArrayAdapter(rootView.context,android.R.layout.simple_spinner_item, list)
                    spinnerCity.adapter = arrayAdapter


                }
                if(resProvince == "MISAMIS OCCIDENTAL"){
                    list = (resources.getStringArray(R.array.cityOccidental) as Array<*>).map { it.toString() }
                    arrayAdapter = ArrayAdapter(rootView.context,android.R.layout.simple_spinner_item, list)
                    spinnerCity.adapter = arrayAdapter


                }
                if(resProvince == "MISAMIS ORIENTAL"){
                    list = (resources.getStringArray(R.array.cityOriental) as Array<*>).map { it.toString() }
                    arrayAdapter = ArrayAdapter(rootView.context,android.R.layout.simple_spinner_item, list)
                    spinnerCity.adapter = arrayAdapter


                }
                getCity()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        return rootView
    }
    fun getCity() {
        spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                resCity = parent?.getItemAtPosition(position).toString()
                Log.wtf("chekcity",resCity)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                resCity = ""
            }

        }
    }
    fun getValues(){
        initFields()
        getGeneralInformationStationary.userId = PrefsUtil.getUser(requireContext())?.userId
        getGeneralInformationStationary.userName = PrefsUtil.getUser(requireContext())?.username
        getGeneralInformationStationary.year = getValue(rootView,R.id.txt_year)
        getGeneralInformationStationary.province = getSpinnerValue(rootView, R.id.spinner_areaProvince, SpinnerEnum.FROM_RESOURCE_XML, R.array.mobileProvinceList)
        getGeneralInformationStationary.city = resCity
        getGeneralInformationStationary.mother_company = getValue(rootView,R.id.txt_mCompany)
        getGeneralInformationStationary.company = getValue(rootView,R.id.txt_nameCompany)
        getGeneralInformationStationary.address = getValue(rootView,R.id.txt_address)
        getGeneralInformationStationary.industry_type = resindustry
        getGeneralInformationStationary.other_industry_type = getValue(rootView,R.id.txt_others)
    }
    fun initFields(){
        /*on select function*/
        val tx_others = rootView.findViewById<View>(R.id.txt_others)
        var industry_type = ""
        tx_others.visibility = View.GONE
        rootView.findViewById<AppCompatSpinner>(R.id.spinner_industry).onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                industry_type = parent?.getItemAtPosition(position).toString()
                resindustry = industry_type
                when {
                    industry_type == "Others" -> {
                        tx_others.visibility = View.VISIBLE
                    }
                    else -> {
                        tx_others.visibility = View.GONE
                    }
                }
                resindustryOthers = getValue(rootView,R.id.txt_others)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        /*on select function end*/
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
                var list : List<String> = listOf()

                if(resProvince == "BUKIDNON"){
                    list = (resources.getStringArray(R.array.cityBukidnon) as Array<*>).map { it.toString() }
                    arrayAdapter = ArrayAdapter(rootView.context,android.R.layout.simple_spinner_item, list)
                    spinnerCity.adapter = arrayAdapter


                }
                if(resProvince == "CAMIGUIN"){
                    list = (resources.getStringArray(R.array.cityCamiguin) as Array<*>).map { it.toString() }
                    arrayAdapter = ArrayAdapter(rootView.context,android.R.layout.simple_spinner_item, list)
                    spinnerCity.adapter = arrayAdapter


                }
                if(resProvince == "LANAO DEL NORTE"){
                    list = (resources.getStringArray(R.array.cityLanao) as Array<*>).map { it.toString() }
                    arrayAdapter = ArrayAdapter(rootView.context,android.R.layout.simple_spinner_item, list)
                    spinnerCity.adapter = arrayAdapter


                }
                if(resProvince == "MISAMIS OCCIDENTAL"){
                    list = (resources.getStringArray(R.array.cityOccidental) as Array<*>).map { it.toString() }
                    arrayAdapter = ArrayAdapter(rootView.context,android.R.layout.simple_spinner_item, list)
                    spinnerCity.adapter = arrayAdapter


                }
                if(resProvince == "MISAMIS ORIENTAL"){
                    list = (resources.getStringArray(R.array.cityOriental) as Array<*>).map { it.toString() }
                    arrayAdapter = ArrayAdapter(rootView.context,android.R.layout.simple_spinner_item, list)
                    spinnerCity.adapter = arrayAdapter


                }
                if(list.contains(getGeneralInformationArea.city.toString())){
                    spinnerCity.setSelection(list.indexOf(getGeneralInformationArea.city))
                }else{
                    spinnerCity.setSelection(0)
                }
                getCity()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        return spinnerProvince

    }

    fun setFields(){
        initFields()
        checkProvince()
        setValue(rootView,getGeneralInformationStationary.year,R.id.txt_year)
        setValue(rootView,getGeneralInformationStationary.mother_company,R.id.txt_mCompany)
        setValue(rootView,getGeneralInformationStationary.company,R.id.txt_nameCompany)
        setValue(rootView,getGeneralInformationStationary.address,R.id.txt_address)
        setValue(rootView,getGeneralInformationStationary.other_industry_type,R.id.txt_others)
        setSpinnerValues(rootView,R.id.spinner_areaProvince,SpinnerEnum.FROM_RESOURCE_XML,resources.getStringArray(R.array.mobileProvinceList),
            getGeneralInformationStationary.province.toString()
        )
        setSpinnerValues(rootView,R.id.spinner_industry,SpinnerEnum.FROM_RESOURCE_XML,resources.getStringArray(R.array.industryTypeArray),
            getGeneralInformationStationary.industry_type.toString()
        )
    }
}