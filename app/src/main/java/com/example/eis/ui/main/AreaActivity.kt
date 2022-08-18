package com.example.eis.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.eis.R
import com.example.eis.ui.adapters.AreaAdapter
import com.example.eis.ui.adapters.MobileAdapter
import com.example.eis.ui.adapters.OnAreaListener
import com.example.eis.ui.adapters.OnMobileListener
import com.example.eis.ui.base.BaseActivity
import com.example.eis.ui.models.Area
import com.example.eis.ui.models.Mobile

class AreaActivity : BaseActivity() {

    private lateinit var srlArea: SwipeRefreshLayout
    private lateinit var rvArea: RecyclerView
    private lateinit var areaAdapter: AreaAdapter
    private var testList: ArrayList<Area> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area)
        initViews()
        initFunctions()
        initAreaList()
    }
    private fun initViews() {
        srlArea = findViewById(R.id.srl_stationary)
        rvArea = findViewById(R.id.rv_stationary)

    }

    private fun initFunctions() {
        srlArea.setOnRefreshListener {
            initAreaList()
        }
        findViewById<AppCompatImageButton>(R.id.btn_back).setOnClickListener{
            startActivity(Intent(this,DashboardActivity::class.java))
            animateToLeft()
            finish()
        }
        findViewById<Button>(R.id.addBtn).setOnClickListener{
            startActivity(Intent(this,AreaFormActivity::class.java))
            animateToRight()
            finish()
        }
    }
    fun initAreaList() {
        testList.add(
            Area(
                1,
                "Structural Fire",
                "2021",
                "Quezon City",
                "Pending"
            )
        )

        areaAdapter = AreaAdapter(this, testList, object: OnAreaListener {
            override fun onAreaSelected(area: Area) {
                val intent = Intent(this@AreaActivity, AreaFormActivity::class.java)
                startActivity(intent)
                animateToRight()
            }
        })
        rvArea.adapter = areaAdapter
        srlArea.isRefreshing = false
    }
}