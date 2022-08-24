package com.example.eis.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.eis.R
import com.example.eis.ui.adapters.AreaAdapter
import com.example.eis.ui.adapters.MobileAdapter
import com.example.eis.ui.adapters.OnAreaListener
import com.example.eis.ui.adapters.OnMobileListener
import com.example.eis.ui.api.ApiAction
import com.example.eis.ui.api.OnApiRequestListener
import com.example.eis.ui.base.BaseActivity
import com.example.eis.ui.models.Area
import com.example.eis.ui.models.AreaList
import com.example.eis.ui.models.Mobile
import com.example.eis.ui.models.request.AreaListRequest
import com.example.eis.ui.models.request.MobileListRequest
import com.example.eis.ui.models.response.AreaListResponse
import com.example.eis.ui.models.response.MobileListResponse
import com.example.eis.ui.utils.PrefsUtil

class AreaActivity : BaseActivity(), OnApiRequestListener {

    private lateinit var srlArea: SwipeRefreshLayout
    private lateinit var rvArea: RecyclerView
    private lateinit var areaAdapter: AreaAdapter
    private var areaList: ArrayList<Area> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area)
        initViews()
        initFunctions()
        initAreaList()
        getAreaList()
    }
    private fun initViews() {
        srlArea = findViewById(R.id.srl_stationary)
        rvArea = findViewById(R.id.rv_stationary)

    }

    private fun initFunctions() {
        srlArea.setOnRefreshListener {
            getAreaList()
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

        areaAdapter = AreaAdapter(this, areaList, object: OnAreaListener {
            override fun onAreaSelected(area: Area) {
                val intent = Intent(this@AreaActivity, AreaFormEdit::class.java)
                intent.putExtra("general_id", area.id)
                startActivity(intent)
                animateToRight()
            }
        })
        rvArea.adapter = areaAdapter
        srlArea.isRefreshing = false
    }

    private fun getAreaList() {
        PrefsUtil.getUser(this).let {
            if (it != null) {
                apiRequest.getAreaList(AreaListRequest(1, 1000))
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
            ApiAction.GET_AREA_LIST -> {
                val arealist = result as AreaListResponse
                updateAreaList(arealist)
                Log.wtf("errornot",arealist.toString())
            }
        }
    }

    private fun updateAreaList(areaListResponse: AreaListResponse){
        areaList = ArrayList()
        areaListResponse.lists
            .forEach {
                areaList.add(
                    Area(
                        it.generalId,
                        it.year,
                        it.province,
                        it.city,
                        it.status_1,
                        it.count
                    )
                )
            }
        Log.wtf("checkwtd", areaList.toString() )
        initAreaList()
    }
}