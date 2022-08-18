package com.example.eis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.eis.R
import com.example.eis.databinding.FragmentMobileFirstBinding
import com.example.eis.databinding.FragmentMobileSecondBinding
import com.example.eis.ui.base.BaseFragment
import com.example.eis.ui.models.Vehicles

class MobileSecondFragment: BaseFragment() {

    private lateinit var rootView: View
    private lateinit var add: Button
    private lateinit var linearVehicle: LinearLayoutCompat
    private val vehicleInputs = mutableListOf<Pair<Int,MutableList<View>>>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMobileSecondBinding>(
            inflater,
            R.layout.fragment_mobile_second,
            container,
            false)

        rootView = binding.root
        add = rootView.findViewById(R.id.btn_add)

        linearVehicle = rootView.findViewById(R.id.vehicle_container)
        add.setOnClickListener{
            addVehicle(inflater, linearVehicle,vehicleInputs)
        }
//        add.performClick()
        return rootView
    }

    fun setFields(){
        val vehicleList = getGeneralInformationMobile.vehicles

        if (vehicleList.isNotEmpty())
            vehicleList.forEach{_ ->
                add.performClick()
                setVehicle(vehicleInputs,vehicleList)
            }
//        if (vehicleList.isNotEmpty())
//            setVehicle(vehicleInputs,vehicleList)
    }
    fun getValues(){
        getVehicle(vehicleInputs)
    }
    fun getValueEdit(){
        getVehicleRequest.clear()
        getVehicleEdit(vehicleInputs)
    }
}