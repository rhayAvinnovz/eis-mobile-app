package com.example.eis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.DataBindingUtil
import com.example.eis.R
import com.example.eis.databinding.FragmentStationarySecondBinding
import com.example.eis.ui.base.BaseFragment

class StationarySecondFragment: BaseFragment() {

    private lateinit var rootView: View
    private lateinit var linearPLant: LinearLayoutCompat
    private lateinit var add: Button
    private val plantInputs = mutableListOf<Pair<Int,MutableList<View>>>()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentStationarySecondBinding>(
            inflater,
            R.layout.fragment_stationary_second,
            container,
            false)

        rootView = binding.root
        add = rootView.findViewById(R.id.btn_add)
        linearPLant = rootView.findViewById(R.id.plant_container)

        add.setOnClickListener{
            addPlant(inflater, linearPLant,plantInputs)
        }

        return rootView
    }

    fun setFields(){
        val PlantList = getGeneralInformationStationary.plants

        if (PlantList.isNotEmpty())
            PlantList.forEach{_ ->
                add.performClick()
                setPLants(plantInputs,PlantList)
            }
    }

    fun getValues(){
        getPlant(plantInputs)
    }

    fun getValueEdit(){
        getPlantRequest.clear()
        getPlantEdit(plantInputs)
    }
}