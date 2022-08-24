package com.example.eis.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.eis.R
import com.example.eis.ui.adapters.MobileAdapter
import com.example.eis.ui.adapters.OnMobileListener
import com.example.eis.ui.adapters.OnStationaryListener
import com.example.eis.ui.adapters.StationaryAdapter
import com.example.eis.ui.api.ApiAction
import com.example.eis.ui.api.OnApiRequestListener
import com.example.eis.ui.base.BaseActivity
import com.example.eis.ui.models.Mobile
import com.example.eis.ui.models.Stationary
import com.example.eis.ui.models.request.MobileListRequest
import com.example.eis.ui.models.response.MobileListResponse
import com.example.eis.ui.utils.PrefsUtil

class MobileActivity : BaseActivity(), OnApiRequestListener {

    private lateinit var srlMobile: SwipeRefreshLayout
    private lateinit var rvMobile: RecyclerView
    private lateinit var mobileAdapter: MobileAdapter
    private var mobileList: ArrayList<Mobile> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile)
        initViews()
        initFunctions()
        initMobileList()
        getMobileList()
    }

    private fun initViews() {
        srlMobile = findViewById(R.id.srl_stationary)
        rvMobile = findViewById(R.id.rv_stationary)

    }

    private fun initFunctions() {
        srlMobile.setOnRefreshListener {
            getMobileList()
            initMobileList()
        }
        findViewById<AppCompatImageButton>(R.id.btn_back).setOnClickListener{
            startActivity(Intent(this,DashboardActivity::class.java))
            animateToLeft()
            finish()
        }
        findViewById<Button>(R.id.addBtn).setOnClickListener{
            val intent = Intent(this@MobileActivity, MobileFormActivity::class.java)
//            intent.putExtra("general_id", 0)
            startActivity(intent)
            animateToRight()
            finish()
        }
    }
    fun initMobileList() {
        mobileAdapter = MobileAdapter(this, mobileList, object: OnMobileListener {
            override fun onMobileSelected(mobile: Mobile) {
                val intent = Intent(this@MobileActivity, MobileFormEdit::class.java)
                intent.putExtra("general_id", mobile.id)
                startActivity(intent)
                animateToRight()
//                showMessage(mobile.id.toString())
            }
        })
        rvMobile.adapter = mobileAdapter
        srlMobile.isRefreshing = false
    }

    private fun getMobileList() {
        PrefsUtil.getUser(this).let {
            if (it != null) {
                apiRequest.getMobileList(MobileListRequest(1, 1000))
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
            ApiAction.GET_MOBILE_LIST -> {
                val mobilelist = result as MobileListResponse
                updateMobileList(mobilelist)
                Log.wtf("errornot",mobilelist.toString())
            }
        }
    }

    private fun updateMobileList(mobileListResponse: MobileListResponse){
        mobileList = ArrayList()
        mobileListResponse.lists
            .forEach {
                mobileList.add(
                    Mobile(
                        it.generalId,
                        it.category,
                        it.year,
                        it.province,
                        it.status_1,
                        it.count
                    )
                )
            }
        Log.wtf("checkwtd", mobileList.toString() )
        initMobileList()
    }

}