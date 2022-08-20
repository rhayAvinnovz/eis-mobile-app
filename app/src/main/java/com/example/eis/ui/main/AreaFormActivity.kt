package com.example.eis.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
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
import com.example.eis.ui.fragments.AreaFirstFragment
import com.example.eis.ui.fragments.AreaSecondFragment
import com.example.eis.ui.fragments.MobileFirstFragment
import com.example.eis.ui.fragments.MobileSecondFragment
import com.example.eis.ui.models.request.SourceRequest
import com.example.eis.ui.models.request.VehicleRequest

class AreaFormActivity : BaseActivity() , OnApiRequestListener{

    private lateinit var vpForms: ViewPager
    private lateinit var fragments: ArrayList<Fragment>
    private var currentIndex = 0

    private lateinit var prev: Button
    private lateinit var next: Button
    private lateinit var space: Space

    private lateinit var firstFragment: AreaFirstFragment
    private lateinit var secondFragment: AreaSecondFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_form)

        firstFragment = AreaFirstFragment()
        secondFragment = AreaSecondFragment()

        initViews()
        initViewPager()
        initFunctions()
        updateIndicator()
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
                next.text = "ADD AREA SOURCE"
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
            startActivity(Intent(this,AreaActivity::class.java))
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
//                generalInformationArea.areas.forEach {
//                    it.sourceId?.let { id ->
//                        if (id.isNotBlank())
//                            apiRequest.deleteVehicle(id.toInt())
//                    }
//                }
                apiRequest.addAreaGeneral(generalInformationArea)
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
    fun addingSources(id: String){
        if(id != "") {

            generalInformationArea.areas.forEach {
                it.generalId = id
                if (it.typeSource!!.isNotBlank() || it.activityRate!!.isNotBlank())
                    apiRequest.addSource(SourceRequest(it))
                Log.wtf("areas", generalInformationArea.areas.toString())
            }
//            Log.wtf("vehicles", generalInformation.vehicles.toString())
            startActivity(Intent(this,AreaActivity::class.java))
            animateToLeft()
            finish()
        }
    }
    override fun onApiRequestSuccess(apiAction: ApiAction, result: Any) {
        super.onApiRequestSuccess(apiAction, result)
        when(apiAction){
            ApiAction.ADD_AREA_GENERAL -> {
                Log.wtf("test", result.toString())

                if (result != "0"){
                    generalInformationArea.generalId = result.toString()
                    secondFragment.getValues()
                    addingSources(generalInformationArea.generalId!!)
                }
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        animateToLeft()
    }
}