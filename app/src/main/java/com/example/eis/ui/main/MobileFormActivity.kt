package com.example.eis.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Space
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.eis.R
import com.example.eis.ui.adapters.FormAdapter
import com.example.eis.ui.api.ApiAction
import com.example.eis.ui.api.OnApiRequestListener
import com.example.eis.ui.base.BaseActivity
import com.example.eis.ui.fragments.MobileFirstFragment
import com.example.eis.ui.fragments.MobileSecondFragment
import com.example.eis.ui.models.request.VehicleRequest

class MobileFormActivity : BaseActivity(), OnApiRequestListener {

    private lateinit var vpForms: ViewPager
    private lateinit var fragments: ArrayList<Fragment>
    private var currentIndex = 0
//    private var intent_id = ""
    private var mobile_id = ""

    private lateinit var prev: Button
    private lateinit var next: Button
    private lateinit var space: Space

    private lateinit var firstFragment: MobileFirstFragment
    private lateinit var secondFragment: MobileSecondFragment



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile_form)

        firstFragment = MobileFirstFragment()
        secondFragment = MobileSecondFragment()

//        val ss:String = intent.getStringExtra("general_id").toString()
//        intent_id = ss
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
    }

    private fun updateNavigation() {
        when (currentIndex % fragments.size) {
            0 -> {
                prev.visibility = View.GONE
                next.visibility = View.VISIBLE
                space.visibility = View.GONE
                next.text = "ADD VEHICLE"
            }
            1 -> {
                prev.visibility = View.VISIBLE
                next.visibility = View.VISIBLE
                space.visibility = View.VISIBLE
                next.text = "SUBMIT"
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
                    firstFragment.getValues()
                    generalInformation.vehicles.forEach {
                        it.vehicleId?.let { id ->
                            if (id.isNotBlank())
                                apiRequest.deleteVehicle(id.toInt())
                        }
                    }
                    apiRequest.addMobileGeneral(generalInformation)
                }
                //TODO API IMPLEMENTATION

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
    fun addingVehicles(id: String){
        if(id != "") {
//            Log.wtf("vehicles", generalInformation.vehicles.toString())

            generalInformation.vehicles.forEach {
                it.generalId = id
                if (it.noVehicle!!.isNotBlank() || it.vktValue!!.isNotBlank())
                    apiRequest.addVehicle(VehicleRequest(it))
                Log.wtf("vehicles", generalInformation.vehicles.toString())
            }
//            Log.wtf("vehicles", generalInformation.vehicles.toString())
            startActivity(Intent(this,MobileActivity::class.java))
            animateToLeft()
            finish()
        }
    }
    override fun onApiRequestSuccess(apiAction: ApiAction, result: Any) {
        super.onApiRequestSuccess(apiAction, result)
        when(apiAction){
            ApiAction.ADD_MOBILE_GENERAL -> {
                Log.wtf("test", result.toString())
                mobile_id = result.toString()

                if (result != "0"){
                    generalInformation.generalId = result.toString()
                    secondFragment.getValues()
                    addingVehicles(generalInformation.generalId!!)
                }
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        animateToLeft()
    }
}