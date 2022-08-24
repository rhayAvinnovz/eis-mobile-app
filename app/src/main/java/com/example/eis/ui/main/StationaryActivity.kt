package com.example.eis.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.eis.R
import com.example.eis.ui.adapters.OnStationaryListener
import com.example.eis.ui.adapters.StationaryAdapter
import com.example.eis.ui.api.ApiAction
import com.example.eis.ui.api.OnApiRequestListener
import com.example.eis.ui.base.BaseActivity
import com.example.eis.ui.models.Area
import com.example.eis.ui.models.Stationary
import com.example.eis.ui.models.request.AreaListRequest
import com.example.eis.ui.models.request.StationaryListRequest
import com.example.eis.ui.models.response.AreaListResponse
import com.example.eis.ui.models.response.StationaryListResponse
import com.example.eis.ui.utils.PrefsUtil


class StationaryActivity : BaseActivity(), OnApiRequestListener {
    private lateinit var srlStationary: SwipeRefreshLayout
    private lateinit var rvStationary: RecyclerView
    private lateinit var stationaryAdapter: StationaryAdapter
    private var stationaryList: ArrayList<Stationary> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stationary)
        initViews()
        initFunctions()
        initStationaryList()
        getStationaryList()
    }
    private fun initViews() {
        srlStationary = findViewById(R.id.srl_stationary)
        rvStationary = findViewById(R.id.rv_stationary)
    }

    private fun initFunctions() {
        srlStationary.setOnRefreshListener {
            getStationaryList()
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

        stationaryAdapter = StationaryAdapter(this, stationaryList, object: OnStationaryListener {
            override fun onStationarySelected(stationary: Stationary) {
                val intent = Intent(this@StationaryActivity, StationaryFormEdit::class.java)
                intent.putExtra("general_id", stationary.id)
                startActivity(intent)
                animateToRight()
            }
        })
        rvStationary.adapter = stationaryAdapter
        srlStationary.isRefreshing = false
    }

    private fun getStationaryList() {
        PrefsUtil.getUser(this).let {
            if (it != null) {
                apiRequest.getStationaryList(StationaryListRequest(1, 1000))
            }
        }
    }

    override fun onApiRequestStart(apiAction: ApiAction) {
        super.onApiRequestStart(apiAction)
    }
    override fun onApiRequestFailed(apiAction: ApiAction, t: Throwable) {
        super.onApiRequestFailed(apiAction, t)
        showMessage(t.localizedMessage)
        Log.wtf("error",t.localizedMessage)
    }

    override fun onApiRequestSuccess(apiAction: ApiAction, result: Any) {
        super.onApiRequestSuccess(apiAction, result)
        when (apiAction) {
            ApiAction.GET_STATIONARY_LIST -> {
                val stationaryList = result as StationaryListResponse
                updateStationaryList(stationaryList)
                Log.wtf("errornot",stationaryList.toString())
            }
        }
    }
    private fun updateStationaryList(stationaryListResponse: StationaryListResponse){
        stationaryList = ArrayList()
        stationaryListResponse.lists
            .forEach {
                stationaryList.add(
                    Stationary(
                        it.generalId,
                        it.company,
                        it.year,
                        it.province,
                        it.city,
                        it.status_1,
                        it.count
                    )
                )
            }
        Log.wtf("checkwtd", stationaryList.toString() )
        initStationaryList()
    }
}