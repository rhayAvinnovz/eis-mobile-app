package com.example.eis.ui.base

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import com.example.eis.R
import com.google.android.material.textfield.TextInputEditText
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.view.forEachIndexed
import com.example.eis.ui.main.AreaFormEdit
import com.example.eis.ui.main.MobileFormEdit
import com.example.eis.ui.main.StationaryFormEdit
import com.example.eis.ui.models.*
import com.example.eis.ui.models.request.PlantRequest
import com.example.eis.ui.models.request.SourceRequest
import com.example.eis.ui.models.request.VehicleRequest
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


open class BaseFragment: Fragment() {



    fun getValue(view: View, id: Int): String {
        return view.findViewById<View>(id).findViewById<TextInputEditText>(R.id.et_input_field).text.toString()
    }
    fun setValue(view: View, value: String?, id: Int) {
        view.findViewById<View>(id).findViewById<TextInputEditText>(R.id.et_input_field).setText(value ?: "")
    }

    fun getSpinnerValue(view: View, id: Int, spinnerEnum: SpinnerEnum, objects: Any?): String{

        val spinner = view.findViewById<AppCompatSpinner>(id)

        var id = when(spinnerEnum){
            SpinnerEnum.FROM_RESOURCE_XML -> spinner.selectedItem
        }

        return id.toString()

    }

    fun setSpinnerValues(view: View, id: Int, spinnerEnum: SpinnerEnum, objects: Any, value: String): AppCompatSpinner {

        val spinner = view.findViewById<AppCompatSpinner>(id)

        var arrayAdapter: ArrayAdapter<String>

        var list = when (spinnerEnum) {
            SpinnerEnum.FROM_RESOURCE_XML -> (objects as Array<*>).map { it.toString() }
        }

        arrayAdapter = ArrayAdapter(view.context,android.R.layout.simple_spinner_item, list)
        spinner.adapter = arrayAdapter

        spinner.setSelection(list.indexOf(value.trim()))

        return spinner

    }

    enum class SpinnerEnum {
        FROM_RESOURCE_XML
    }

    fun addPlant(
        inflater: LayoutInflater,
        linearPlant: LinearLayoutCompat,
        plantInputs: MutableList<Pair<Int,MutableList<View>>>,
    ){
        if(linearPlant.childCount == 1){
            val layoutParams = LinearLayoutCompat.LayoutParams(
                linearPlant.layoutParams.width,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            layoutParams.weight = 5.0f
            linearPlant.layoutParams = layoutParams
        }
        val apsiInputs = mutableListOf<View>()

        val view: View = inflater.inflate(R.layout.plant_details_entry, linearPlant, false)
        var linearApsi: LinearLayoutCompat = view.findViewById(R.id.apsiContainer)
        val textView = view.findViewById<TextView>(R.id.tx_plant)
        val btn_addApsi = view.findViewById<TextView>(R.id.btn_addApsi)
        val buttonDelete = view.findViewById<AppCompatButton>(R.id.btn_delete)

        /*views*/
        val list:MutableList<View> = mutableListOf()
        val value1 = view.findViewById<View>(R.id.txt_plantName)
        val value2 = view.findViewById<View>(R.id.txt_plantAddress)
        val value3 = view.findViewById<View>(R.id.apsiContainer)

        list.add(value1)
        list.add(value2)
        list.add(value3)
        /*views end*/

        linearPlant.addView(view)
        plantInputs.add(Pair(linearPlant.childCount,list))
        textView.text = "Plant ${linearPlant.childCount} Details"
        buttonDelete.setOnClickListener {
            plantInputs.remove(Pair(linearPlant.childCount,list))
            linearPlant.removeView(view)
        }
        btn_addApsi.setOnClickListener{
            this.addApsi(inflater,linearApsi,apsiInputs)
        }

    }

    fun addApsi(
        inflater: LayoutInflater,
        linearApsi: LinearLayoutCompat,
        ApsiInputs: MutableList<View>
    ){
        if(linearApsi.childCount == 1){
            val layoutParams = LinearLayoutCompat.LayoutParams(
                linearApsi.layoutParams.width,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            layoutParams.weight = 5.0f
            linearApsi.layoutParams = layoutParams
        }
        val apcdInputs = mutableListOf<Pair<View, View>>()

        val view: View = inflater.inflate(R.layout.apsi_details_entry, linearApsi, false)
        var linearApcd: LinearLayoutCompat = view.findViewById(R.id.apcdContainer)
        val btn_addApcd = view.findViewById<TextView>(R.id.btn_addApcd)
        val textView = view.findViewById<TextView>(R.id.tx_apsi)
        val buttonDelete = view.findViewById<AppCompatButton>(R.id.btn_delete)
        val spinnerApsi = view.findViewById<AppCompatSpinner>(R.id.spinner_apsiType)
        val layoutApsiOthers = view.findViewById<LinearLayoutCompat>(R.id.layout_apsi_others)
        val layoutUnitOthers = view.findViewById<TextInputLayout>(R.id.apsiUnitLayout)
        val spinnerFuel = view.findViewById<AppCompatSpinner>(R.id.spinner_fuelType)
        val layoutFuelOthers = view.findViewById<LinearLayoutCompat>(R.id.layout_fuel_others)

        /*Functions*/
        linearApsi.addView(view)
        textView.text = "Apsi ${linearApsi.childCount} Details"
        btn_addApcd.setOnClickListener{
            this.addApcd(inflater,linearApcd,apcdInputs)
        }
        buttonDelete.setOnClickListener {
            linearApsi.removeView(view)
        }
        view.findViewById<AppCompatSpinner>(R.id.spinner_apsiType).onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val checkApsi = parent?.getItemAtPosition(position).toString()
                if (checkApsi == "Others") {
                    layoutApsiOthers.visibility = View.VISIBLE
                    layoutUnitOthers.visibility = View.VISIBLE
                } else {
                    layoutApsiOthers.visibility = View.GONE
                    layoutUnitOthers.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        view.findViewById<AppCompatSpinner>(R.id.spinner_fuelType).onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val checkApsi = parent?.getItemAtPosition(position).toString()
                if (checkApsi == "Others") {
                    spinnerFuel.visibility = View.GONE
                    layoutFuelOthers.visibility = View.VISIBLE
                } else {
                    spinnerFuel.visibility = View.VISIBLE
                    layoutFuelOthers.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        view.findViewById<TextView>(R.id.btn_deleteApsiOthers).setOnClickListener{
            spinnerApsi.visibility = View.VISIBLE
            layoutApsiOthers.visibility = View.GONE
            layoutUnitOthers.visibility = View.GONE

        }
        view.findViewById<TextView>(R.id.btn_deleteFuelOthers).setOnClickListener{
            spinnerFuel.visibility = View.VISIBLE
            layoutFuelOthers.visibility = View.GONE
        }

        /*End Functions*/
    }

    fun addApcd(
        inflater: LayoutInflater,
        linearApcd: LinearLayoutCompat,
        ApcdInputs: MutableList<Pair<View, View>>
    ){
        if(linearApcd.childCount == 1){
            val layoutParams = LinearLayoutCompat.LayoutParams(
                linearApcd.layoutParams.width,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            layoutParams.weight = 5.0f
            linearApcd.layoutParams = layoutParams
        }
        val view: View = inflater.inflate(R.layout.apcd_details_entry, linearApcd, false)
        val buttonDelete = view.findViewById<TextView>(R.id.txt_delete)
        val spinnerApcd = view.findViewById<AppCompatSpinner>(R.id.apcd_type)
        val layoutApcdOthers = view.findViewById<LinearLayoutCompat>(R.id.layout_apcd_others)

        /*Functions*/
        linearApcd.addView(view)
        buttonDelete.setOnClickListener {
            linearApcd.removeView(view)
        }
        view.findViewById<AppCompatSpinner>(R.id.apcd_type).onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val checkApsi = parent?.getItemAtPosition(position).toString()
                if (checkApsi == "Others") {
                    spinnerApcd.visibility = View.GONE
                    layoutApcdOthers.visibility = View.VISIBLE
                } else {
                    spinnerApcd.visibility = View.VISIBLE
                    layoutApcdOthers.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        view.findViewById<TextView>(R.id.btn_deleteApcdOthers).setOnClickListener{
            spinnerApcd.visibility = View.VISIBLE
            layoutApcdOthers.visibility = View.GONE
        }
        /*End Functions*/
    }

    fun addVehicle(
        inflater: LayoutInflater,
        linearVehicle: LinearLayoutCompat,
        vehicleInputs: MutableList<Pair<Int,MutableList<View>>>,
    ){
        if(linearVehicle.childCount == 1){
            val layoutParams = LinearLayoutCompat.LayoutParams(
                linearVehicle.layoutParams.width,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            layoutParams.weight = 5.0f
            linearVehicle.layoutParams = layoutParams
        }

        val view: View = inflater.inflate(R.layout.vehicle_details_entry, linearVehicle, false)
        val vType = view.findViewById<View>(R.id.spinner_vehicleType)
        val no_vehicle = view.findViewById<View>(R.id.txt_noVehicle)
        val fType =  view.findViewById<View>(R.id.spinner_fuelType)
        val vkt = view.findViewById<View>(R.id.txt_vkt)
        val psc = view.findViewById<View>(R.id.txt_sulfurContent)
        val trip = view.findViewById<View>(R.id.txt_tripPerDay)
        val co = view.findViewById<View>(R.id.txt_co)
        val nox = view.findViewById<View>(R.id.txt_nox)
        val pm = view.findViewById<View>(R.id.txt_pm)
        val sox = view.findViewById<View>(R.id.txt_sox)
        val voc = view.findViewById<View>(R.id.txt_voc)
        val textView = view.findViewById<TextView>(R.id.tx_vehicle)
        val buttonDelete = view.findViewById<AppCompatButton>(R.id.btn_delete)

        val list: MutableList<View> = mutableListOf()
        list.add(vType)
        list.add(no_vehicle)
        list.add(fType)
        list.add(vkt)
        list.add(psc)
        list.add(trip)
        list.add(co)
        list.add(nox)
        list.add(pm)
        list.add(sox)
        list.add(voc)


        vehicleInputs.add(Pair(linearVehicle.childCount,list))
        linearVehicle.addView(view)

        buttonDelete.setOnClickListener {
            linearVehicle.removeView(view)
            vehicleInputs.remove(Pair(linearVehicle.childCount,list))
        }

        textView.text = "Vehicle ${linearVehicle.childCount} Details"

    }
    fun setVehicle(vehicleInputs: MutableList<Pair<Int,MutableList<View>>>,vehicleList: List<Vehicles>){

        vehicleInputs.forEachIndexed { index, it ->
            if(index >= vehicleList.size)
                return@forEachIndexed

            val value1 = it.second[0].findViewById<AppCompatSpinner>(R.id.spinner_vehicleType)
            val value2 = it.second[1].findViewById<TextInputEditText>(R.id.txt_noVehicle)
            val value3 = it.second[2].findViewById<AppCompatSpinner>(R.id.spinner_fuelType)
            val value4 = it.second[3].findViewById<TextInputEditText>(R.id.txt_vkt)
            val value5 = it.second[4].findViewById<TextInputEditText>(R.id.txt_sulfurContent)
            val value6 = it.second[5].findViewById<TextInputEditText>(R.id.txt_tripPerDay)
            val value7 = it.second[6].findViewById<TextInputEditText>(R.id.txt_co)
            val value8 = it.second[7].findViewById<TextInputEditText>(R.id.txt_nox)
            val value9 = it.second[8].findViewById<TextInputEditText>(R.id.txt_pm)
            val value10 = it.second[9].findViewById<TextInputEditText>(R.id.txt_sox)
            val value11 = it.second[10].findViewById<TextInputEditText>(R.id.txt_voc)

            setSpinnerValues(it.second[0],R.id.spinner_vehicleType,SpinnerEnum.FROM_RESOURCE_XML,resources.getStringArray(R.array.vehicleTypeList),
                vehicleList[index].vehicleType!!
            )
            value2.setText(vehicleList[index].noVehicle)
            setSpinnerValues(it.second[2],R.id.spinner_fuelType,SpinnerEnum.FROM_RESOURCE_XML,resources.getStringArray(R.array.mobileFuelType),
                vehicleList[index].fuelType!!
            )
            value4.setText(vehicleList[index].vktValue)
            value5.setText(vehicleList[index].percentSulfur)
            value6.setText(vehicleList[index].tripValue)
            value7.setText(vehicleList[index].coValue)
            value8.setText(vehicleList[index].noxValue)
            value9.setText(vehicleList[index].pmValue)
            value10.setText(vehicleList[index].soxValue)
            value11.setText(vehicleList[index].vocValue)
        }
    }

    fun getVehicle(vehicleInputs: MutableList<Pair<Int,MutableList<View>>>) {

        getGeneralInformationMobile.vehicles.clear()


        vehicleInputs.forEach{
            val addList = Vehicles()


            val value1 = it.second[0].findViewById<AppCompatSpinner>(R.id.spinner_vehicleType).selectedItem.toString()
            val value2 = it.second[1].findViewById<TextInputEditText>(R.id.txt_noVehicle).text.toString()
            val value3 = it.second[2].findViewById<AppCompatSpinner>(R.id.spinner_fuelType).selectedItem.toString()
            val value4 = it.second[3].findViewById<TextInputEditText>(R.id.txt_vkt).text.toString()
            val value5 = it.second[4].findViewById<TextInputEditText>(R.id.txt_sulfurContent).text.toString()
            val value6 = it.second[5].findViewById<TextInputEditText>(R.id.txt_tripPerDay).text.toString()
            val value7 = it.second[6].findViewById<TextInputEditText>(R.id.txt_co).text.toString()
            val value8 = it.second[7].findViewById<TextInputEditText>(R.id.txt_nox).text.toString()
            val value9 = it.second[8].findViewById<TextInputEditText>(R.id.txt_pm).text.toString()
            val value10 = it.second[9].findViewById<TextInputEditText>(R.id.txt_sox).text.toString()
            val value11 = it.second[10].findViewById<TextInputEditText>(R.id.txt_voc).text.toString()

//            addList.generalId = getGeneralInformationMobile.generalId
            addList.vehicleType = value1
            addList.noVehicle = value2
            addList.fuelType = value3
            addList.vktValue = value4
            addList.percentSulfur = value5
            addList.tripValue = value6
            addList.coValue = value7
            addList.noxValue = value8
            addList.pmValue = value9
            addList.soxValue = value10
            addList.vocValue = value11
            getGeneralInformationMobile.vehicles.add(addList)


        }
        Log.wtf("vehicles", getGeneralInformationMobile.vehicles.toString())


    }
    fun getVehicleEdit(vehicleInputs: MutableList<Pair<Int,MutableList<View>>>) {

        getGeneralInformationMobile.vehicles.clear()


        vehicleInputs.forEach{
            val addList = VehicleRequest()


            val value1 = it.second[0].findViewById<AppCompatSpinner>(R.id.spinner_vehicleType).selectedItem.toString()
            val value2 = it.second[1].findViewById<TextInputEditText>(R.id.txt_noVehicle).text.toString()
            val value3 = it.second[2].findViewById<AppCompatSpinner>(R.id.spinner_fuelType).selectedItem.toString()
            val value4 = it.second[3].findViewById<TextInputEditText>(R.id.txt_vkt).text.toString()
            val value5 = it.second[4].findViewById<TextInputEditText>(R.id.txt_sulfurContent).text.toString()
            val value6 = it.second[5].findViewById<TextInputEditText>(R.id.txt_tripPerDay).text.toString()
            val value7 = it.second[6].findViewById<TextInputEditText>(R.id.txt_co).text.toString()
            val value8 = it.second[7].findViewById<TextInputEditText>(R.id.txt_nox).text.toString()
            val value9 = it.second[8].findViewById<TextInputEditText>(R.id.txt_pm).text.toString()
            val value10 = it.second[9].findViewById<TextInputEditText>(R.id.txt_sox).text.toString()
            val value11 = it.second[10].findViewById<TextInputEditText>(R.id.txt_voc).text.toString()

            addList.generalId = getGeneralInformationMobile.generalId
            addList.vehicleType = value1
            addList.noVehicle = value2
            addList.fuelType = value3
            addList.vktValue = value4
            addList.percentSulfur = value5
            addList.tripValue = value6
            addList.coValue = value7
            addList.noxValue = value8
            addList.pmValue = value9
            addList.soxValue = value10
            addList.vocValue = value11
            getVehicleRequest.add(addList)


        }
        Log.wtf("vehicles", getVehicleRequest.toString())


    }

    fun addArea(
        inflater: LayoutInflater,
        linearArea: LinearLayoutCompat,
        AreaInputs: MutableList<Pair<Int, MutableList<View>>>,
    ){
        if(linearArea.childCount == 1){
            val layoutParams = LinearLayoutCompat.LayoutParams(
                linearArea.layoutParams.width,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            layoutParams.weight = 5.0f
            linearArea.layoutParams = layoutParams
        }

        /*function  buttons*/
//        val coordinatesInputs = mutableListOf<Pair<View,View>>()
        val listUtm:MutableList<Pair<View,View>> = mutableListOf()
        val view: View = inflater.inflate(R.layout.area_source_entry, linearArea, false)
        var linearUtm: LinearLayoutCompat = view.findViewById(R.id.utmContainer)
        val btn_addUtm = view.findViewById<TextView>(R.id.btn_addUtm)
        val textView = view.findViewById<TextView>(R.id.tx_vehicle)
        val buttonDelete = view.findViewById<AppCompatButton>(R.id.btn_delete)
        /*function buttons end*/

        /*views*/
        val list:MutableList<View> = mutableListOf()
        val value1 = view.findViewById<View>(R.id.source_type)
        val value2 = view.findViewById<View>(R.id.txt_actRate)
        val value3 = view.findViewById<View>(R.id.txt_duration)
        val value4 = view.findViewById<View>(R.id.txt_others)
        val value5 = view.findViewById<View>(R.id.txt_address)
        val value6 = view.findViewById<View>(R.id.txt_co)
        val value7 = view.findViewById<View>(R.id.txt_nox)
        val value8 = view.findViewById<View>(R.id.txt_pm)
        val value9 = view.findViewById<View>(R.id.txt_sox)
        val value10 = view.findViewById<View>(R.id.txt_voc)
        val value11 = view.findViewById<View>(R.id.utmContainer)

        list.add(value1)
        list.add(value2)
        list.add(value3)
        list.add(value4)
        list.add(value5)
        list.add(value6)
        list.add(value7)
        list.add(value8)
        list.add(value9)
        list.add(value10)
        list.add(value11)
        /*views end*/



        /*on select function*/
        val tx_duration = view.findViewById<TextInputLayout>(R.id.layout_duration)
        val tx_others = view.findViewById<TextInputLayout>(R.id.layout_others)
        var source_type = ""
        tx_others.visibility = View.GONE
        tx_duration.visibility = View.GONE
        view.findViewById<AppCompatSpinner>(R.id.source_type).onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                source_type = parent?.getItemAtPosition(position).toString()
                if(source_type == "Residential construction" || source_type == "Non-residential construction" ||
                    source_type == "Road construction" ){
                    tx_duration.visibility = View.VISIBLE
                    tx_others.visibility = View.GONE
                }else if(source_type == "Others"){
                    tx_others.visibility = View.VISIBLE
                    tx_duration.visibility = View.GONE
                }else{
                    tx_others.visibility = View.GONE
                    tx_duration.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        /*on select function end*/



        linearArea.addView(view)
        AreaInputs.add(Pair(linearArea.childCount,list))
        textView.text = "Area Source ${linearArea.indexOfChild(view)+1} Details"

        buttonDelete.setOnClickListener {
            linearArea.removeView(view)
//            vehicleInputs.remove(Pair(linearVehicle.childCount,list))
        }

        btn_addUtm.setOnClickListener{
            this.addAreaCoordinates(inflater,linearUtm,listUtm)
//            Log.wtf("countingParent",linearArea.childCount.toString())

        }

    }

    fun getArea(areaInputs: MutableList<Pair<Int,MutableList<View>>>) {

        getGeneralInformationArea.areas.clear()


        areaInputs.forEach{
            val addList = AreaModel()


            val value1 = it.second[0].findViewById<AppCompatSpinner>(R.id.source_type).selectedItem.toString()
            val value2 = it.second[1].findViewById<TextInputEditText>(R.id.txt_actRate).text.toString()
            val value3 = it.second[2].findViewById<TextInputEditText>(R.id.txt_duration).text.toString()
            val value4 = it.second[3].findViewById<TextInputEditText>(R.id.txt_others).text.toString()
            val value5 = it.second[4].findViewById<TextInputEditText>(R.id.txt_address).text.toString()
            val value6 = it.second[5].findViewById<TextInputEditText>(R.id.txt_co).text.toString()
            val value7 = it.second[6].findViewById<TextInputEditText>(R.id.txt_nox).text.toString()
            val value8 = it.second[7].findViewById<TextInputEditText>(R.id.txt_pm).text.toString()
            val value9 = it.second[8].findViewById<TextInputEditText>(R.id.txt_sox).text.toString()
            val value10 = it.second[9].findViewById<TextInputEditText>(R.id.txt_voc).text.toString()
            var lin = it.second[10].findViewById<LinearLayoutCompat>(R.id.utmContainer)

//            addList.generalId = getGeneralInformationArea.generalId
            addList.typeSource = value1
            addList.activityRate = value2
            addList.durationMonths = value3
            addList.otherSource = value4
            addList.address = value5
            addList.coValue = value6
            addList.noxValue = value7
            addList.pmValue = value8
            addList.soxValue = value9
            addList.vocValue = value10
            addList.utm = getUtmValues(lin)

            getGeneralInformationArea.areas.add(addList)


        }
        Log.wtf("areas", getGeneralInformationArea.areas.toString())


    }

    fun getAreaEdit(areaInputs: MutableList<Pair<Int,MutableList<View>>>) {

        getGeneralInformationArea.areas.clear()


        areaInputs.forEach{
            val addList = SourceRequest()


            val value1 = it.second[0].findViewById<AppCompatSpinner>(R.id.source_type).selectedItem.toString()
            val value2 = it.second[1].findViewById<TextInputEditText>(R.id.txt_actRate).text.toString()
            val value3 = it.second[2].findViewById<TextInputEditText>(R.id.txt_duration).text.toString()
            val value4 = it.second[3].findViewById<TextInputEditText>(R.id.txt_others).text.toString()
            val value5 = it.second[4].findViewById<TextInputEditText>(R.id.txt_address).text.toString()
            val value6 = it.second[5].findViewById<TextInputEditText>(R.id.txt_co).text.toString()
            val value7 = it.second[6].findViewById<TextInputEditText>(R.id.txt_nox).text.toString()
            val value8 = it.second[7].findViewById<TextInputEditText>(R.id.txt_pm).text.toString()
            val value9 = it.second[8].findViewById<TextInputEditText>(R.id.txt_sox).text.toString()
            val value10 = it.second[9].findViewById<TextInputEditText>(R.id.txt_voc).text.toString()
            var lin = it.second[10].findViewById<LinearLayoutCompat>(R.id.utmContainer)

            addList.generalId = getGeneralInformationArea.generalId
            addList.typeSource = value1
            addList.activityRate = value2
            addList.durationMonths = value3
            addList.otherSource = value4
            addList.address = value5
            addList.coValue = value6
            addList.noxValue = value7
            addList.pmValue = value8
            addList.soxValue = value9
            addList.vocValue = value10
            addList.utm = getUtmValues(lin)

           getSourceRequest.add(addList)


        }
//        Log.wtf("areas", getGeneralInformationArea.areas.toString())
    }
    fun getPlant(plantInputs: MutableList<Pair<Int,MutableList<View>>>) {

        getGeneralInformationStationary.plants.clear()


        plantInputs.forEach{
            val addList = PlantModel()


            val value1 = it.second[0].findViewById<TextInputEditText>(R.id.txt_plantName).text.toString()
            val value2 = it.second[1].findViewById<TextInputEditText>(R.id.txt_plantAddress).text.toString()
            var lin = it.second[2].findViewById<LinearLayoutCompat>(R.id.apsiContainer)

//            addList.generalId = getGeneralInformationArea.generalId
            addList.plantName = value1
            addList.plantAddress = value2
            addList.apsi = getApsiValues(lin)

            getGeneralInformationStationary.plants.add(addList)


        }
        Log.wtf("plants", getGeneralInformationStationary.plants.toString())


    }

    fun getPlantEdit(plantInputs: MutableList<Pair<Int,MutableList<View>>>) {

        getGeneralInformationStationary.plants.clear()


        plantInputs.forEach{
            val addList = PlantRequest()


            val value1 = it.second[0].findViewById<TextInputEditText>(R.id.txt_plantName).text.toString()
            val value2 = it.second[1].findViewById<TextInputEditText>(R.id.txt_plantAddress).text.toString()
            var lin = it.second[2].findViewById<LinearLayoutCompat>(R.id.apsiContainer)

            addList.generalId = getGeneralInformationStationary.generalId
            addList.plantName = value1
            addList.plantAddress = value2
            addList.apsi = getApsiValues(lin)

            getPlantRequest.add(addList)


        }
        Log.wtf("plants", getGeneralInformationStationary.plants.toString())


    }
    fun addAreaCoordinates(
        inflater: LayoutInflater,
        linearCoordinates: LinearLayoutCompat,
        CoorInputs: MutableList<Pair<View,View>>
    ){

        val view: View = inflater.inflate(R.layout.area_coordinates_entry, linearCoordinates,false)
        val buttonDelete = view.findViewById<TextView>(R.id.txt_delete)
        val get_northing = view.findViewById<View>(R.id.txt_utmNorthing)
        val get_easting = view.findViewById<View>(R.id.txt_utmEasting)

        CoorInputs.add(Pair(get_northing,get_easting))
        linearCoordinates.addView(view)

        buttonDelete.setOnClickListener {
            linearCoordinates.removeView(view)
            CoorInputs.remove(Pair(get_northing,get_easting))
        }
    }

    fun getUtmValues(linearLayout: LinearLayoutCompat) : String {


        val list = mutableListOf<UtmValues>()
        list.clear()
            for (i in 0 until linearLayout.childCount) {
                /*Fetching data from multiple child views*/
                var addList = UtmValues()
                val v: View = linearLayout.findViewById<LinearLayoutCompat>(R.id.utmContainer).getChildAt(i)

                val checknorth = v.findViewById<TextInputEditText>(R.id.txt_utmNorthing).text.toString()
                val checkeast = v.findViewById<TextInputEditText>(R.id.txt_utmEasting).text.toString()
                if(checknorth.isNotEmpty() || checkeast.isNotEmpty()) {
                    addList.northingInput = checknorth
                    addList.eastingInput = checkeast
                }
                list.add(addList)
            }
        return Gson().toJson(list).toString()
    }

    fun getApsiValues(linearLayout: LinearLayoutCompat) : ArrayList<ApsiModel> {


        val list: ArrayList<ApsiModel> = arrayListOf()
        list.clear()
        for (i in 0 until linearLayout.childCount) {
            /*Fetching data from multiple child views*/
            var addList = ApsiModel()
            val v: View = linearLayout.findViewById<LinearLayoutCompat>(R.id.apsiContainer).getChildAt(i)

            val apsiOthers = v.findViewById<TextInputEditText>(R.id.apsi_type_others).text.toString()
            val apsiUnitOthers = v.findViewById<TextInputEditText>(R.id.txt_apsiUnitOthers).text.toString()
            val apsiCapacity = v.findViewById<TextInputEditText>(R.id.apsi_capacity).text.toString()
            val utmEasting = v.findViewById<TextInputEditText>(R.id.txt_utmEasting).text.toString()
            val utmNorthing = v.findViewById<TextInputEditText>(R.id.txt_utmNorthing).text.toString()
            val fuelType = v.findViewById<AppCompatSpinner>(R.id.spinner_fuelType).selectedItem.toString()
            val fuelOthers = v.findViewById<TextInputEditText>(R.id.fuel_type_others).text.toString()
            val fuelRate = v.findViewById<TextInputEditText>(R.id.txt_fuelRate).text.toString()
            val fuelConsumption = v.findViewById<TextInputEditText>(R.id.txt_fuelConsumption).text.toString()
            val operatingHours = v.findViewById<TextInputEditText>(R.id.txt_operatingHours).text.toString()
            val fuelUnit = v.findViewById<AppCompatSpinner>(R.id.spinner_fuelUnit).selectedItem.toString()
            val coValue = v.findViewById<TextInputEditText>(R.id.txt_co).text.toString()
            val noxValue = v.findViewById<TextInputEditText>(R.id.txt_nox).text.toString()
            val pmValue = v.findViewById<TextInputEditText>(R.id.txt_pm).text.toString()
            val soxValue = v.findViewById<TextInputEditText>(R.id.txt_sox).text.toString()
            val vocValue = v.findViewById<TextInputEditText>(R.id.txt_voc).text.toString()
            val apcd = v.findViewById<LinearLayoutCompat>(R.id.apcdContainer)

            if(v.findViewById<AppCompatSpinner>(R.id.spinner_apsiType).selectedItem.toString().isNotEmpty() || apsiCapacity.isNotEmpty()) {
                var type = v.findViewById<AppCompatSpinner>(R.id.spinner_apsiType).selectedItem.toString()
                if(type == "Boiler"){
                    addList.apsiType = "boiler_hp"
                    addList.apsiUnit = "HP"
                    addList.apsiSize = when {
                        apsiCapacity.toInt() <= 250 -> {
                            "Medium"
                        }
                        apsiCapacity.toInt() >= 251 -> {
                            "Large"
                        }
                        else -> {""}
                    }
                }
                if(type == "Generator"){
                    addList.apsiType = "generator_kw"
                    addList.apsiUnit = "KW"
                    addList.apsiSize = when {
                        apsiCapacity.toInt() <= 250 -> {
                            "Medium"
                        }
                        apsiCapacity.toInt() >= 251 -> {
                            "Large"
                        }
                        else -> {""}
                    }
                }
                if(type == "Others"){
                    if(apsiOthers.isNotEmpty() && apsiUnitOthers.isNotEmpty()) {
                        addList.apsiType = type
                        addList.otherApsi = apsiOthers
                        addList.apsiUnit = apsiUnitOthers
                        addList.apsiSize = ""
                    }
                }
                addList.apsiCapacity = apsiCapacity
                addList.utmEasting = utmEasting
                addList.utmNorthing = utmNorthing
                addList.fuelType = fuelType
                addList.otherFuel = if (fuelOthers.isNotEmpty()) fuelOthers else "0"
                addList.fuelRate = fuelRate
                addList.fuelUsed = fuelConsumption
                addList.fuelUnit = fuelUnit
                addList.operatingHours = operatingHours
                addList.apcd = getApcdValues(apcd)
                addList.coValue = coValue
                addList.noxValue = noxValue
                addList.pmValue = pmValue
                addList.soxValue = soxValue
                addList.vocValue = vocValue
            }
            list.add(addList)
        }
        return list
    }


    fun getApcdValues(linearLayout: LinearLayoutCompat) : String {


        val list = mutableListOf<ApcdValues>()
        list.clear()
        for (i in 0 until linearLayout.childCount) {
            /*Fetching data from multiple child views*/
            var addList = ApcdValues()
            val v: View = linearLayout.findViewById<LinearLayoutCompat>(R.id.apcdContainer).getChildAt(i)

            val apcdType = v.findViewById<AppCompatSpinner>(R.id.apcd_type).selectedItem.toString()
            val apcdOthers = v.findViewById<TextInputEditText>(R.id.apcd_type_others).text.toString()
            val apcdEfficiency = v.findViewById<TextInputEditText>(R.id.txt_apcdEfficiency).text.toString()
            if(apcdType.isNotEmpty() || apcdEfficiency.isNotEmpty()) {
                addList.apcd = apcdType
                addList.other_apcd = apcdOthers
                addList.apsd_efficiency = apcdEfficiency
            }
            list.add(addList)
        }
        return Gson().toJson(list).toString()
    }

    fun setApcdValues(linearLayout: LinearLayoutCompat,arrayList: ArrayList<ApcdValues>){

        for (i in 0 until arrayList.count()) {
            Log.wtf("something here", arrayList[0].apcd)
            val inflater: LayoutInflater = LayoutInflater.from(context)
            val view: View = inflater.inflate(R.layout.apcd_details_entry, linearLayout, false)

            val spinnerApcd = view.findViewById<AppCompatSpinner>(R.id.apcd_type)
            val apcdOthers = view.findViewById<TextInputEditText>(R.id.apcd_type_others)
            val layoutApcdOthers = view.findViewById<LinearLayoutCompat>(R.id.layout_apcd_others)
            val textApcd =
                view.findViewById<TextInputEditText>(R.id.txt_apcdEfficiency)

            setSpinnerValues(
                view,
                R.id.apcd_type,
                SpinnerEnum.FROM_RESOURCE_XML,
                resources.getStringArray(R.array.apcdType),
                arrayList[i].apcd.toString()
            )
//            textApcd.setText(arrayList[0].apsd_efficiency)

        }
//        val inflater: LayoutInflater = LayoutInflater.from(context)
//        val view: View = inflater.inflate(R.layout.apcd_details_entry, apcd, false)
//        val spinnerApcd = view.findViewById<AppCompatSpinner>(R.id.apcd_type)
//        val apcdOthers = view.findViewById<TextInputEditText>(R.id.apcd_type_others)
//        val layoutApcdOthers = view.findViewById<LinearLayoutCompat>(R.id.layout_apcd_others)
//        val textApcd =
//            view.findViewById<TextInputEditText>(R.id.txt_apcdEfficiency)
//
//        textApcd.setText(sList[0].apsd_efficiency)
//        setSpinnerValues(
//            view,
//            R.id.apcd_type,
//            SpinnerEnum.FROM_RESOURCE_XML,
//            resources.getStringArray(R.array.apcdType),
//            sList[i].apcd.toString()
//        )
    }

    fun setSources(sourceInputs: MutableList<Pair<Int,MutableList<View>>>,sourceList: List<AreaModel>){

        sourceInputs.forEachIndexed { index, it ->
            if(index >= sourceList.size)
                return@forEachIndexed

            val value1 = it.second[0].findViewById<AppCompatSpinner>(R.id.source_type)
            val value2 = it.second[1].findViewById<TextInputEditText>(R.id.txt_actRate)
            val value3 = it.second[2].findViewById<TextInputEditText>(R.id.txt_duration)
            val value4 = it.second[3].findViewById<TextInputEditText>(R.id.txt_others)
            val value5 = it.second[4].findViewById<TextInputEditText>(R.id.txt_address)
            val value6 = it.second[5].findViewById<TextInputEditText>(R.id.txt_co)
            val value7 = it.second[6].findViewById<TextInputEditText>(R.id.txt_nox)
            val value8 = it.second[7].findViewById<TextInputEditText>(R.id.txt_pm)
            val value9 = it.second[8].findViewById<TextInputEditText>(R.id.txt_sox)
            val value10 = it.second[9].findViewById<TextInputEditText>(R.id.txt_voc)
            var lin = it.second[10].findViewById<LinearLayoutCompat>(R.id.utmContainer)

            setSpinnerValues(it.second[0],R.id.source_type,SpinnerEnum.FROM_RESOURCE_XML,resources.getStringArray(R.array.typeSourceLists),
                sourceList[index].typeSource!!
            )
            value2.setText(sourceList[index].activityRate)
            value3.setText(sourceList[index].durationMonths)
            value4.setText(sourceList[index].otherSource)
            value5.setText(sourceList[index].address)
            value6.setText(sourceList[index].coValue)
            value7.setText(sourceList[index].noxValue)
            value8.setText(sourceList[index].pmValue)
            value9.setText(sourceList[index].soxValue)
            value10.setText(sourceList[index].vocValue)

            val sourcesList: Type = object : TypeToken<ArrayList<UtmValues?>?>() {}.type

            val sList: ArrayList<UtmValues> = Gson().fromJson(sourceList[index].utm, sourcesList)
            Log.wtf("listing",sList.toString())
            if (sList.isNotEmpty())
                sList.forEach{
                    val inflater: LayoutInflater = LayoutInflater.from(context)
                    val view: View = inflater.inflate(R.layout.area_coordinates_entry, lin,false)
                    val buttonDelete = view.findViewById<TextView>(R.id.txt_delete)

                    lin.addView(view)
                    buttonDelete.setOnClickListener {
                        lin.removeView(view)
                    }
                }
                sList.forEachIndexed { indexnew, it ->

                    val v: View = lin.findViewById<LinearLayoutCompat>(R.id.utmContainer).getChildAt(indexnew)
                    val checknorth = v.findViewById<TextInputEditText>(R.id.txt_utmNorthing)
                    val checkeast = v.findViewById<TextInputEditText>(R.id.txt_utmEasting)
                    checknorth.setText(sList[indexnew].northingInput)
                    checkeast.setText(sList[indexnew].eastingInput)


                }
        }

    }

    fun setPLants(plantsInputs: MutableList<Pair<Int,MutableList<View>>>,plantsList: List<PlantModel>) {
//        val inflater: LayoutInflater = LayoutInflater.from(context)
        plantsInputs.forEachIndexed { index, it ->
            if (index >= plantsList.size)
                return@forEachIndexed

            val value1 = it.second[0].findViewById<TextInputEditText>(R.id.txt_plantName)
            val value2 = it.second[1].findViewById<TextInputEditText>(R.id.txt_plantAddress)
            var lin = it.second[2].findViewById<LinearLayoutCompat>(R.id.apsiContainer)


//            setSpinnerValues(it.second[0],R.id.source_type,SpinnerEnum.FROM_RESOURCE_XML,resources.getStringArray(R.array.typeSourceLists),
//                sourceList[index].typeSource!!
//            )
//            val apsiInputs = mutableListOf<View>()
            value1.setText(plantsList[index].plantName)
            value2.setText(plantsList[index].plantAddress)
            plantsList[index].apsi.forEach { _ ->
                setApsi(lin,plantsList[index].apsi)
            }
//            setApsi(lin,plantsList[index].apsi)
        }
    }

    fun setApsi(lin: LinearLayoutCompat,apsi: List<ApsiModel>) {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.apsi_details_entry, lin, false)
        if (apsi.isNotEmpty()) {
            apsi.forEachIndexed { index, it ->
                val apcdInputs = mutableListOf<Pair<View, View>>()

                val buttonDelete = view.findViewById<AppCompatButton>(R.id.btn_delete)
                val tx_apsi = view.findViewById<TextView>(R.id.tx_apsi)
                var linearApcd: LinearLayoutCompat = view.findViewById(R.id.apcdContainer)
                val btn_addApcd = view.findViewById<TextView>(R.id.btn_addApcd)

                lin.addView(view)
                tx_apsi.setText("Apsi ${lin.childCount} Details")
                btn_addApcd.setOnClickListener {
                    this.addApcd(inflater, linearApcd, apcdInputs)
                }
                buttonDelete.setOnClickListener {
                    lin.removeView(view)
                }
                val spinnerApsi = view.findViewById<AppCompatSpinner>(R.id.spinner_apsiType)
                val layoutApsiOthers =
                    view.findViewById<LinearLayoutCompat>(R.id.layout_apsi_others)
                val layoutUnitOthers = view.findViewById<TextInputLayout>(R.id.apsiUnitLayout)
                val spinnerFuel = view.findViewById<AppCompatSpinner>(R.id.spinner_fuelType)
                val layoutFuelOthers =
                    view.findViewById<LinearLayoutCompat>(R.id.layout_fuel_others)
                val apsiOthers = view.findViewById<TextInputEditText>(R.id.apsi_type_others)
                val apsiUnitOthers = view.findViewById<TextInputEditText>(R.id.txt_apsiUnitOthers)
                val apsiCapacity = view.findViewById<TextInputEditText>(R.id.apsi_capacity)
                val utmEasting = view.findViewById<TextInputEditText>(R.id.txt_utmEasting)
                val utmNorthing = view.findViewById<TextInputEditText>(R.id.txt_utmNorthing)
                val fuelOthers = view.findViewById<TextInputEditText>(R.id.fuel_type_others)
                val fuelRate = view.findViewById<TextInputEditText>(R.id.txt_fuelRate)
                val fuelConsumption =
                    view.findViewById<TextInputEditText>(R.id.txt_fuelConsumption)
                val operatingHours = view.findViewById<TextInputEditText>(R.id.txt_operatingHours)
                val coValue = view.findViewById<TextInputEditText>(R.id.txt_co)
                val noxValue = view.findViewById<TextInputEditText>(R.id.txt_nox)
                val pmValue = view.findViewById<TextInputEditText>(R.id.txt_pm)
                val soxValue = view.findViewById<TextInputEditText>(R.id.txt_sox)
                val vocValue = view.findViewById<TextInputEditText>(R.id.txt_voc)
                val apcd = view.findViewById<LinearLayoutCompat>(R.id.apcdContainer)
                apsiOthers.setText(it.otherApsi)
                apsiUnitOthers.setText(it.apsiUnit)
                apsiCapacity.setText(it.apsiCapacity)
                utmEasting.setText(it.utmEasting)
                utmNorthing.setText(it.utmNorthing)
                fuelOthers.setText(it.otherFuel)
                fuelRate.setText(it.fuelRate)
                fuelConsumption.setText(it.fuelUsed)
                operatingHours.setText(it.operatingHours)
                coValue.setText(it.coValue)
                noxValue.setText(it.noxValue)
                pmValue.setText(it.pmValue)
                soxValue.setText(it.soxValue)
                vocValue.setText(it.vocValue)
                Log.wtf("type", it.apsiType.toString())
                when {
                    it.apsiType.toString() == "boiler_hp" -> {
                        Log.wtf("type", "trueB")
                        setSpinnerValues(
                            view,
                            R.id.spinner_apsiType,
                            SpinnerEnum.FROM_RESOURCE_XML,
                            resources.getStringArray(R.array.apsiType),
                            "Boiler"
                        )
                    }
                    it.apsiType.toString() == "generator_kw" -> {
                        Log.wtf("type", "trueG")
                        setSpinnerValues(
                            view,
                            R.id.spinner_apsiType,
                            SpinnerEnum.FROM_RESOURCE_XML,
                            resources.getStringArray(R.array.apsiType),
                            "Generator"
                        )
                    }
                    else -> {
                        setSpinnerValues(
                            view,
                            R.id.spinner_apsiType,
                            SpinnerEnum.FROM_RESOURCE_XML,
                            resources.getStringArray(R.array.apsiType),
                            "Others"
                        )
                    }
                }
                view.findViewById<TextView>(R.id.btn_deleteApsiOthers).setOnClickListener {
                    spinnerApsi.visibility = View.VISIBLE
                    layoutApsiOthers.visibility = View.GONE
                    layoutUnitOthers.visibility = View.GONE

                }
                view.findViewById<TextView>(R.id.btn_deleteFuelOthers).setOnClickListener {
                    spinnerFuel.visibility = View.VISIBLE
                    layoutFuelOthers.visibility = View.GONE
                }
                spinnerApsi.let {
                    it.onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            val checkApsi = parent?.getItemAtPosition(position).toString()
                            if (checkApsi == "Others") {
                                layoutApsiOthers.visibility = View.VISIBLE
                                layoutUnitOthers.visibility = View.VISIBLE
                            } else {
                                layoutApsiOthers.visibility = View.GONE
                                layoutUnitOthers.visibility = View.GONE
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }

                    }
                }
                setSpinnerValues(
                    view,
                    R.id.spinner_fuelType,
                    SpinnerEnum.FROM_RESOURCE_XML,
                    resources.getStringArray(R.array.fuelType),
                    it.fuelType.toString()
                )
                spinnerFuel.let {
                    it.onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            val checkApsi = parent?.getItemAtPosition(position).toString()
                            if (checkApsi == "Others") {
                                spinnerFuel.visibility = View.GONE
                                layoutFuelOthers.visibility = View.VISIBLE
                            } else {
                                spinnerFuel.visibility = View.VISIBLE
                                layoutFuelOthers.visibility = View.GONE
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }

                    }
                }
                setSpinnerValues(
                    view,
                    R.id.spinner_fuelUnit,
                    SpinnerEnum.FROM_RESOURCE_XML,
                    resources.getStringArray(R.array.unitType),
                    it.fuelUnit.toString()
                )

                val apcdList: Type = object : TypeToken<ArrayList<ApcdValues?>?>() {}.type

                val sList: ArrayList<ApcdValues> = Gson().fromJson(it.apcd, apcdList)
                if (sList.isNotEmpty()) {
                    sList.forEachIndexed { index, it ->
                        val inflater: LayoutInflater = LayoutInflater.from(context)
                        val v: View =
                            inflater.inflate(R.layout.apcd_details_entry, apcd, false)
                        val buttonDelete = v.findViewById<TextView>(R.id.txt_delete)
                        val spinnerApcd = v.findViewById<AppCompatSpinner>(R.id.apcd_type)
                        val apcdOthers = v.findViewById<TextInputEditText>(R.id.apcd_type_others)
                        val layoutApcdOthers =
                            v.findViewById<LinearLayoutCompat>(R.id.layout_apcd_others)
                        val textApcd =
                            v.findViewById<TextInputEditText>(R.id.txt_apcdEfficiency)

                        textApcd.setText(sList[index].apsd_efficiency)
                        setSpinnerValues(
                            v,
                            R.id.apcd_type,
                            SpinnerEnum.FROM_RESOURCE_XML,
                            resources.getStringArray(R.array.apcdType),
                            sList[index].apcd.toString()
                        )
                        spinnerApcd.let {
                            it.onItemSelectedListener = object :
                                AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    val checkApsi =
                                        parent?.getItemAtPosition(position).toString()
                                    if (checkApsi == "Others") {
                                        apcdOthers.setText(sList[index].other_apcd)
                                        spinnerApcd.visibility = View.GONE
                                        layoutApcdOthers.visibility = View.VISIBLE
                                    } else {
                                        spinnerApcd.visibility = View.VISIBLE
                                        layoutApcdOthers.visibility = View.GONE
                                    }
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                    TODO("Not yet implemented")
                                }

                            }
                        }
                        apcd.addView(v)
                        buttonDelete.setOnClickListener {
                            apcd.removeView(v)
                        }
                        v.findViewById<TextView>(R.id.btn_deleteApcdOthers).setOnClickListener {
                            spinnerApcd.visibility = View.VISIBLE
                            layoutApcdOthers.visibility = View.GONE
                        }
                    }
                }
            }
//

        }
    }


    protected val getGeneralInformationMobile: MobileGeneral by lazy {
        val activity = requireActivity() as BaseActivity
        activity.getGeneralInfo()
    }

    protected val getGeneralInformationArea: AreaGeneral by lazy {
        val activity = requireActivity() as BaseActivity
        activity.getGeneralInfoArea()
    }

    protected val getGeneralInformationStationary: StationaryGeneral by lazy {
        val activity = requireActivity() as BaseActivity
        activity.getGeneralInfoStationary()
    }

    protected val getVehicleRequest: MutableList<VehicleRequest> by lazy {
        val activity = requireActivity()
        (activity as MobileFormEdit).vehicleRequest
    }

    protected val getSourceRequest: MutableList<SourceRequest> by lazy {
        val activity = requireActivity()
        (activity as AreaFormEdit).sourceRequest
    }

    protected val getPlantRequest: MutableList<PlantRequest> by lazy {
        val activity = requireActivity()
        (activity as StationaryFormEdit).plantRequest
    }
}