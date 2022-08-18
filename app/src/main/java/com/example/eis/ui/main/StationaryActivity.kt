package com.example.eis.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.eis.R
import com.example.eis.ui.adapters.OnStationaryListener
import com.example.eis.ui.adapters.StationaryAdapter
import com.example.eis.ui.base.BaseActivity
import com.example.eis.ui.models.Stationary


class StationaryActivity : BaseActivity() {
    private lateinit var srlStationary: SwipeRefreshLayout
    private lateinit var rvStationary: RecyclerView
    private lateinit var stationaryAdapter: StationaryAdapter
    private var testList: ArrayList<Stationary> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stationary)
        initViews()
        initFunctions()
        initStationaryList()
    }
    private fun initViews() {
        srlStationary = findViewById(R.id.srl_stationary)
        rvStationary = findViewById(R.id.rv_stationary)
    }

    private fun initFunctions() {
        srlStationary.setOnRefreshListener {
            initStationaryList()
        }
        findViewById<AppCompatImageButton>(R.id.btn_back).setOnClickListener{
            startActivity(Intent(this,DashboardActivity::class.java))
            animateToLeft()
            finish()
        }
        findViewById<Button>(R.id.addBtn).setOnClickListener{
            startActivity(Intent(this,StationaryFormActivity::class.java))
            animateToRight()
            finish()
        }
    }
    fun initStationaryList() {
        testList.add(
            Stationary(
                1,
                "Avinnovz",
                "Quezon City",
                "2021",
                "Pending"
            )
        )
        stationaryAdapter = StationaryAdapter(this, testList, object: OnStationaryListener {
            override fun onStationarySelected(stationary: Stationary) {
                val intent = Intent(this@StationaryActivity, StationaryFormActivity::class.java)
                startActivity(intent)
                animateToRight()
            }
        })
        rvStationary.adapter = stationaryAdapter
        srlStationary.isRefreshing = false
    }
}