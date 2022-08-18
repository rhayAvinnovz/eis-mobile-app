package com.example.eis.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Space
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.eis.R
import com.example.eis.databinding.ActivityStationaryFormBinding
import com.example.eis.ui.adapters.FormAdapter
import com.example.eis.ui.base.BaseActivity
import com.example.eis.ui.fragments.StationaryFirstFragment
import com.example.eis.ui.fragments.StationarySecondFragment

class StationaryFormActivity : BaseActivity() {

    private lateinit var vpForms: ViewPager
    private lateinit var fragments: ArrayList<Fragment>
    private var currentIndex = 0

    private lateinit var prev: Button
    private lateinit var next: Button
    private lateinit var space: Space

    private lateinit var firstFragment: StationaryFirstFragment
    private lateinit var secondFragment: StationarySecondFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityStationaryFormBinding>(this,
            R.layout.activity_stationary_form)
        firstFragment = StationaryFirstFragment()
        secondFragment = StationarySecondFragment()
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
                next.text = "ADD PLANT"
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
        vpForms.adapter = FormAdapter(fragments, supportFragmentManager)
        vpForms.offscreenPageLimit = fragments.size
    }


    private fun initFunctions(){
        findViewById<AppCompatImageButton>(R.id.btn_back).setOnClickListener{
            startActivity(Intent(this,StationaryActivity::class.java))
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
    override fun onBackPressed() {
        super.onBackPressed()
        animateToLeft()
    }
}