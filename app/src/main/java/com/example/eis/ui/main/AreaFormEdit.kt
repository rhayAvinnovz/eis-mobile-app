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
import com.example.eis.ui.fragments.AreaFirstFragment
import com.example.eis.ui.fragments.AreaSecondFragment
import com.example.eis.ui.models.AreaGeneral
import com.example.eis.ui.models.MobileGeneral
import com.example.eis.ui.models.request.SourceRequest
import com.example.eis.ui.models.request.VehicleRequest

class AreaFormEdit : BaseActivity(), OnApiRequestListener {

    private lateinit var vpForms: ViewPager
    private lateinit var fragments: ArrayList<Fragment>
    private var currentIndex = 0
    private var intent_id = 0

    private lateinit var prev: Button
    private lateinit var next: Button
    private lateinit var space: Space

    private lateinit var firstFragment: AreaFirstFragment
    private lateinit var secondFragment: AreaSecondFragment
    val sourceRequest = mutableListOf<SourceRequest>()

    private lateinit var loader: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_form)

        firstFragment = AreaFirstFragment()
        secondFragment = AreaSecondFragment()

        val ss:String = intent.getStringExtra("general_id").toString()
        if (ss != null) {
            intent_id = ss.toInt()
        }

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
        loader = findViewById(R.id.loader)
    }

    private fun updateNavigation() {
        when (currentIndex % fragments.size) {
            0 -> {
                prev.visibility = View.GONE
                next.visibility = View.VISIBLE
                space.visibility = View.GONE
                next.text = "SOURCE ENTRIES"
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
                if(intent_id != 0) {
                    firstFragment.getValues()
                    // Delete vehicle here first
                    generalInformationArea.areas.forEach {
                        it.sourceId?.let { id ->
                            if (id.isNotBlank())
                                apiRequest.deleteSource(id.toInt())
                        }
                    }

                    apiRequest.updateAreaGeneral(generalInformationArea)
                    //TODO API IMPLEMENTATION
                }
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
            ApiAction.GET_AREA_GENERAL -> Toast.makeText(this, t.localizedMessage, Toast.LENGTH_LONG).show()
        }

    }
    override fun onApiRequestSuccess(apiAction: ApiAction, result: Any) {
        super.onApiRequestSuccess(apiAction, result)
        when(apiAction){
            ApiAction.GET_AREA_GENERAL -> {
                generalInformationArea = result as AreaGeneral
                firstFragment.setFields()
                secondFragment.setFields()
                hideLoading()
            }
            ApiAction.UPDATE_AREA_GENERAL -> {
                secondFragment.getValueEdit()
                sourceRequest.forEach {
                    if (it.typeSource!!.isBlank())
                        return@forEach
                    apiRequest.addSource(it)
                }
                Log.wtf("sources",sourceRequest.toString())
                startActivity(Intent(this,AreaActivity::class.java))
                animateToLeft()
                finish()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        apiRequest.getAreaGeneral(intent_id)
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