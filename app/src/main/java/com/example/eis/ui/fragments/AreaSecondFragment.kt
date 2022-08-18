package com.example.eis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.DataBindingUtil
import com.example.eis.R
import com.example.eis.databinding.FragmentAreaSecondBinding
import com.example.eis.databinding.FragmentMobileSecondBinding
import com.example.eis.ui.base.BaseFragment

class AreaSecondFragment: BaseFragment() {

    private lateinit var rootView: View
    private lateinit var add: Button
    private lateinit var linearArea: LinearLayoutCompat
    private val areaInputs = mutableListOf<Pair<Int, MutableList<View>>>()
    private val coorInputs = mutableListOf<Pair<View,View>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAreaSecondBinding>(
            inflater,
            R.layout.fragment_area_second,
            container,
            false)

        rootView = binding.root
        add = rootView.findViewById(R.id.btn_add)

        linearArea = rootView.findViewById(R.id.area_container)
        add.setOnClickListener{
            addArea(inflater, linearArea,areaInputs,coorInputs)
        }
        return rootView
    }
    fun getValues(){
        getArea(areaInputs,coorInputs)
    }
}