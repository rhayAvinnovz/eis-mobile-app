package com.example.eis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.DataBindingUtil
import com.example.eis.R
import com.example.eis.databinding.FragmentMobileFirstBinding
import com.example.eis.ui.base.BaseFragment
import com.example.eis.ui.utils.PrefsUtil

class MobileFirstFragment: BaseFragment() {

    private lateinit var rootView: View
    private lateinit var bottomTopLayout: LinearLayoutCompat
    private lateinit var spinnerCategory: Spinner
    var resCategory= ""




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMobileFirstBinding>(
            inflater,
            R.layout.fragment_mobile_first,
            container,
            false)

        rootView = binding.root

        bottomTopLayout = rootView.findViewById(R.id.bottom_top_layout)
        spinnerCategory = rootView.findViewById(R.id.spinner_mobileCat)
        bottomTop()

        return rootView
    }
    fun setFields(){

         setValue(rootView,getGeneralInformationMobile.year,R.id.txt_year)
        if(getGeneralInformationMobile.category == "bottom-top"){
            setSpinnerValues(rootView,R.id.spinner_mobileCat,SpinnerEnum.FROM_RESOURCE_XML,resources.getStringArray(R.array.mobileCategoryList),
                getGeneralInformationMobile.category.toString()
            )

             setValue(rootView,getGeneralInformationMobile.rt_fare,R.id.txt_fare)
             setValue(rootView,getGeneralInformationMobile.utmNorthing,R.id.txt_utmNorthing)
             setValue(rootView,getGeneralInformationMobile.utmEasting,R.id.txt_utmEasting)
            bottomTop()
         }else{
            setSpinnerValues(rootView,R.id.spinner_mobileCat,SpinnerEnum.FROM_RESOURCE_XML,resources.getStringArray(R.array.mobileCategoryList),
                getGeneralInformationMobile.category.toString()
            )
             bottomTop()
         }
        setSpinnerValues(rootView,R.id.spinner_mobileProvince,SpinnerEnum.FROM_RESOURCE_XML,resources.getStringArray(R.array.mobileProvinceList),
            getGeneralInformationMobile.province.toString()
        )

    }

    fun getValues(){
        getGeneralInformationMobile.userId = PrefsUtil.getUser(requireContext())?.userId
        getGeneralInformationMobile.userName = PrefsUtil.getUser(requireContext())?.username
        getGeneralInformationMobile.year = getValue(rootView,R.id.txt_year)
//        getGeneralInformationMobile.category = getSpinnerValue(rootView, R.id.spinner_mobileCat, SpinnerEnum.FROM_RESOURCE_XML, R.array.mobileCategoryList)
        getGeneralInformationMobile.province = getSpinnerValue(rootView, R.id.spinner_mobileProvince, SpinnerEnum.FROM_RESOURCE_XML, R.array.mobileProvinceList)
        if(getSpinnerValue(rootView, R.id.spinner_mobileCat, SpinnerEnum.FROM_RESOURCE_XML, R.array.mobileCategoryList) == "bottom-top"){
            getGeneralInformationMobile.category = "bottom-top"
            getGeneralInformationMobile.rt_fare = getValue(rootView,R.id.txt_fare)
            getGeneralInformationMobile.utmNorthing = getValue(rootView,R.id.txt_utmNorthing)
            getGeneralInformationMobile.utmEasting = getValue(rootView,R.id.txt_utmEasting)
        }else{
            getGeneralInformationMobile.category = "top-bottom"
        }
    }

    fun bottomTop(): Spinner {

        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                resCategory = parent?.getItemAtPosition(position).toString()
                hideLayout()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        return spinnerCategory

    }

    fun hideLayout(){
        when{
            resCategory == "bottom-top" -> {
                bottomTopLayout.visibility = View.VISIBLE
            }
            resCategory == "top-bottom" -> {
                bottomTopLayout.visibility = View.GONE
            }
        }
    }
}