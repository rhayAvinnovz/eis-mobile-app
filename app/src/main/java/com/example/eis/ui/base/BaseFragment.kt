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
import com.example.eis.ui.main.MobileFormEdit
import com.example.eis.ui.models.*
import com.example.eis.ui.models.request.VehicleRequest
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson


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
        plantInputs: MutableList<Pair<View, View>>,
    ){
        if(linearPlant.childCount == 1){
            val layoutParams = LinearLayoutCompat.LayoutParams(
                linearPlant.layoutParams.width,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            layoutParams.weight = 5.0f
            linearPlant.layoutParams = layoutParams
        }
        val apsiInputs = mutableListOf<Pair<View, View>>()

        val view1: View = inflater.inflate(R.layout.plant_details_entry, linearPlant, false)
        val view2: View = inflater.inflate(R.layout.apsi_details_entry, null, false)
        //val view3: View = inflater.inflate(R.layout.apcd_details_entry, linearPlant, false)
        var linearApsi: LinearLayoutCompat = view1.findViewById(R.id.apsiContainer)
        val textView = view1.findViewById<TextView>(R.id.tx_plant)
        val btn_addApsi = view1.findViewById<TextView>(R.id.btn_addApsi)
        val buttonDelete = view1.findViewById<AppCompatButton>(R.id.btn_delete)

        linearPlant.addView(view1)
        textView.text = "Plant ${linearPlant.childCount} Details"
        buttonDelete.setOnClickListener {
            linearPlant.removeView(view1)
//            vehicleInputs.remove(Pair(linearPlant.childCount,list))
        }
        btn_addApsi.setOnClickListener{
            this.addApsi(inflater,linearApsi,apsiInputs)
        }

    }

    fun addApsi(
        inflater: LayoutInflater,
        linearApsi: LinearLayoutCompat,
        ApsiInputs: MutableList<Pair<View, View>>
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
        var count = 1

        val view: View = inflater.inflate(R.layout.apsi_details_entry, linearApsi, false)
        var linearApcd: LinearLayoutCompat = view.findViewById(R.id.apcdContainer)
        val btn_addApcd = view.findViewById<TextView>(R.id.btn_addApcd)
        val textView = view.findViewById<TextView>(R.id.tx_apsi)
        val buttonDelete = view.findViewById<AppCompatButton>(R.id.btn_delete)

        //plantInputs.add(Pair(plantName,plantAddress))
        linearApsi.addView(view)
        textView.text = "Apsi ${linearApsi.childCount} Details"
        btn_addApcd.setOnClickListener{
            this.addApcd(inflater,linearApcd,apcdInputs)
        }
        buttonDelete.setOnClickListener {
            linearApsi.removeView(view)
//            vehicleInputs.remove(Pair(linearPlant.childCount,list))
        }
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

        //plantInputs.add(Pair(plantName,plantAddress))
        linearApcd.addView(view)
        buttonDelete.setOnClickListener {
            linearApcd.removeView(view)
//            vehicleInputs.remove(Pair(linearPlant.childCount,list))
        }
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
            this.addAreaCoordinates(inflater,linearArea.indexOfChild(view),linearUtm,listUtm)
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

            getGeneralInformationArea.areas.add(addList)


        }
        Log.wtf("areas", getGeneralInformationArea.areas.toString())


    }

    fun addAreaCoordinates(
        inflater: LayoutInflater,
        parent_count: Int,
        linearCoordinates: LinearLayoutCompat,
        CoorInputs: MutableList<Pair<View,View>>
    ){

        val view: View = inflater.inflate(R.layout.area_coordinates_entry, linearCoordinates,false)
        val buttonDelete = view.findViewById<TextView>(R.id.txt_delete)
        val get_northing = view.findViewById<View>(R.id.txt_utmNorthing)
        val get_easting = view.findViewById<View>(R.id.txt_utmEasting)

        CoorInputs.add(Pair(get_northing,get_easting))
        linearCoordinates.addView(view)
        Log.wtf("counting",parent_count.toString()+" : "+linearCoordinates.childCount.toString())

        buttonDelete.setOnClickListener {
            linearCoordinates.removeView(view)
            CoorInputs.remove(Pair(get_northing,get_easting))
        }
    }

    fun getUtmValues(linearLayout: LinearLayoutCompat) : String {


        val list = mutableListOf<UtmValues>()
            for (i in 0 until linearLayout.childCount) {
                /*Fetching data from multiple child views*/
                var addList = UtmValues()
                val v: View = linearLayout.findViewById<LinearLayoutCompat>(R.id.utmContainer).getChildAt(i)

                val checknorth = v.findViewById<TextInputEditText>(R.id.txt_utmNorthing).text.toString()
                val checkeast = v.findViewById<TextInputEditText>(R.id.txt_utmEasting).text.toString()
                addList.northingInput = checknorth
                addList.eastingInput = checkeast
                list.add(addList)
            }
        return Gson().toJson(list).toString()
    }

    protected val getGeneralInformationMobile: MobileGeneral by lazy {
        val activity = requireActivity() as BaseActivity
        activity.getGeneralInfo()
    }

    protected val getGeneralInformationArea: AreaGeneral by lazy {
        val activity = requireActivity() as BaseActivity
        activity.getGeneralInfoArea()
    }

    protected val getVehicleRequest: MutableList<VehicleRequest> by lazy {
        val activity = requireActivity()
        (activity as MobileFormEdit).vehicleRequest
    }
}