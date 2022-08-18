package com.example.eis.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Space
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.eis.R
import com.example.eis.ui.adapters.FormAdapter
import com.example.eis.ui.api.ApiAction
import com.example.eis.ui.api.OnApiRequestListener
import com.example.eis.ui.base.BaseActivity
import com.example.eis.ui.fragments.MobileFirstFragment
import com.example.eis.ui.fragments.MobileSecondFragment
import com.example.eis.ui.models.MobileGeneral
import com.example.eis.ui.models.Vehicles
import com.example.eis.ui.models.request.VehicleRequest

class MobileFormEdit : BaseActivity(), OnApiRequestListener {

    private lateinit var vpForms: ViewPager
    private lateinit var fragments: ArrayList<Fragment>
    private var currentIndex = 0
    private var intent_id = 0
    private var mobile_id = ""

    private lateinit var prev: Button
    private lateinit var next: Button
    private lateinit var space: Space

    private lateinit var firstFragment: MobileFirstFragment
    private lateinit var secondFragment: MobileSecondFragment
    val vehicleRequest = mutableListOf<VehicleRequest>()

    private lateinit var loader: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile_form)

        firstFragment = MobileFirstFragment()
        secondFragment = MobileSecondFragment()

        val ss:String = intent.getStringExtra("general_id").toString()
        if (ss != null) {
            intent_id = ss.toInt()
        }
        initViews()
        initViewPager()
        updateIndicator()
        initFunctions()
        updateNavigation()
    }

    private fun initViews() {
        vpForms = findViewById(R.id.viewPager_Form)
        next = findViewById(R.id.next)
        prev = findViewById(R.id.prev)
        space = findViewById(R.id.space)
        loader = findViewById(R.id.loader)

    }

    private fun updateNavigation() {
        when (currentIndex % fragments.size) {
            0 -> {
                prev.visibility = View.GONE
                next.visibility = View.VISIBLE
                space.visibility = View.GONE
                next.text = "VEHICLE ENTRIES"
            }
            1 -> {
                prev.visibility = View.VISIBLE
                next.visibility = View.VISIBLE
                space.visibility = View.VISIBLE
                next.text = "UPDATE"
            }
        }
    }

    private fun initViewPager() {
        fragments = ArrayList()
        fragments.add(firstFragment)
        fragments.add(secondFragment)
        //fragments.add(secondFragment)
        vpForms.adapter = FormAdapter(fragments, supportFragmentManager)
        vpForms.offscreenPageLimit = fragments.size
    }


    private fun initFunctions(){
        findViewById<AppCompatImageButton>(R.id.btn_back).setOnClickListener{
            startActivity(Intent(this,MobileActivity::class.java))
            animateToLeft()
            finish()
        }
        prev.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                updateIndicator()
            }
            vpForms.currentItem = currentIndex % fragments.size
        }

        next.setOnClickListener {
            if (currentIndex < fragments.size - 1) {
                currentIndex++
                updateIndicator()
            }
            else {
                if(intent_id != 0){
                    firstFragment.getValues()

//                    Log.wtf("vehicle", getGeneralInfo().vehicles.toString())
                    // Delete products here first

                    generalInformation.vehicles.forEach {
                        it.vehicleId?.let { id ->
                            if (id.isNotBlank())
                                apiRequest.deleteVehicle(id.toInt())
                        }
                    }
//                     Products is repopulated here as well
//                    secondFragment.getValues()
                    // Add product

//                    apiRequest.addMobileGeneral(generalInformation)
                    apiRequest.updateMobileGeneral(generalInformation)

//                    if(!generalInformation.generalId?.isEmpty()!!) {
//                        generalInformation.vehicles.forEach {
//                            it.generalId = generalInformation.generalId
//                            if (it.noVehicle!!.isNotBlank() || it.vktValue!!.isNotBlank())
//                                apiRequest.addVehicle(VehicleRequest(it))
//                        }
//                        startActivity(Intent(this, MobileActivity::class.java))
//                        animateToLeft()
//                        finish()
//                    }


                }
//                    secondFragment.getValues()
//                // Delete products here first
//                generalInformation.vehicles.forEach {
//                    it.vehicleId?.let { id ->
//                        if (id.isNotBlank())
//                            apiRequest.deleteVehicle(id.toInt())
//                    }
//                }
//                // Products is repopulated here as well
//                firstFragment.getValues()
//                // Add product
//                generalInformation.vehicles.forEach {
//                    if (it.productLines!!.isNotBlank() || it.productionRate!!.isNotBlank() || it.productionRate!!.isNotBlank())
//                        apiRequest.addVehicle(ProductRequest(it))
//                }


                //TODO API IMPLEMENTATION
            }

            vpForms.currentItem = currentIndex % fragments.size
        }
    }

    private fun updateIndicator() {
        hideKeyboard()
        deactivateAll()
        updateNavigation()
        findViewById<View>(R.id.indicator_one).findViewById<TextView>(R.id.txt_indicator).text = "Step 1"
        findViewById<View>(R.id.indicator_two).findViewById<TextView>(R.id.txt_indicator).text = "Step 2"
        when (currentIndex) {
            0 -> setActive(R.id.indicator_one)
            1 -> setActive(R.id.indicator_two)
        }
    }

    private fun deactivateAll() {
        deactivate(R.id.indicator_one)
        deactivate(R.id.indicator_two)
    }

    private fun setActive(id: Int) {
        findViewById<View>(id).findViewById<View>(R.id.indicator_bg).setBackgroundResource(R.drawable.indicator_shape)
    }

    private fun deactivate(id: Int) {
        findViewById<View>(id).findViewById<View>(R.id.indicator_bg).setBackgroundResource(R.drawable.indicator_deactivate)
    }
    fun updateVehicles(){

//            Log.wtf("vehicles", generalInformation.vehicles.toString())
        // Delete vehicles here first
        generalInformation.vehicles.forEach {
            it.vehicleId?.let { id ->
                if (id.isNotBlank())
                    apiRequest.deleteVehicle(id.toInt())
            }
        }
        vehicleRequest.forEach {
            if (it.vehicleType!!.isBlank())
                return@forEach
            apiRequest.addVehicle(it)
        }

//            generalInformation.vehicles.forEach {
//                it.generalId = id
//                if (it.noVehicle!!.isNotBlank() || it.vktValue!!.isNotBlank())
//                    apiRequest.addVehicle(VehicleRequest(it))
//                Log.wtf("vehicles", generalInformation.vehicles.toString())
//            }
//            Log.wtf("vehicles", generalInformation.vehicles.toString())
        startActivity(Intent(this,MobileActivity::class.java))
        animateToLeft()
        finish()
    }

    override fun onApiRequestStart(apiAction: ApiAction) {
        super.onApiRequestStart(apiAction)
        if (isFinishing || isDestroyed)
            return
        showLoading()
    }
    override fun onApiRequestFailed(apiAction: ApiAction, t: Throwable) {
        super.onApiRequestFailed(apiAction, t)
        if (isFinishing || isDestroyed)
            return
        hideLoading()
        when (apiAction){
            ApiAction.GET_MOBILE_GENERAL -> Toast.makeText(this, t.localizedMessage, Toast.LENGTH_LONG).show()
//            ApiAction.UPDATE_CIR_AQM -> Toast.makeText(this, t.localizedMessage, Toast.LENGTH_LONG).show()
        }

    }
    override fun onApiRequestSuccess(apiAction: ApiAction, result: Any) {
        super.onApiRequestSuccess(apiAction, result)
        when(apiAction){
            ApiAction.GET_MOBILE_GENERAL -> {
                generalInformation = result as MobileGeneral
                firstFragment.setFields()
                secondFragment.setFields()
                hideLoading()
            }
            ApiAction.UPDATE_MOBILE_GENERAL -> {
                secondFragment.getValueEdit()
                updateVehicles()
            }
//            ApiAction.GET_CIR_AQM -> {
//                if (result is CirAqmHwmResponse){
//                    cirResponse = result
//                    cirRequest.cirId = cirResponse.cirId
//                    secondFragment.setInspectionFields()
//                    thirdFragment.setInspectionFields()
//                    fourthFragment.setFieldsValue()
//                    fifthFragment.setFieldsValue()
//                    sixthFragment.setAttachments()
//                    hideLoading()
//                }
//            }
        }
        hideLoading()
    }
    override fun onResume() {
        super.onResume()
        apiRequest.getMobileGeneral(intent_id)
        //apiRequest.getGeneralInformation(1)
    }
    private fun showLoading() {
        loader.visibility = View.VISIBLE
    }
    private fun hideLoading() {
        loader.visibility = View.GONE
    }
    override fun onBackPressed() {
        super.onBackPressed()
        animateToLeft()
    }
}