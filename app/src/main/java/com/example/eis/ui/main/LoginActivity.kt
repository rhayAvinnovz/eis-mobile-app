package com.example.eis.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.eis.R
import com.example.eis.ui.api.ApiAction
import com.example.eis.ui.api.OnApiRequestListener
import com.example.eis.ui.base.BaseActivity
import com.example.eis.ui.models.response.UserResponse
import com.example.eis.ui.utils.PrefsUtil

class LoginActivity : BaseActivity(), OnApiRequestListener {

    private lateinit var loader: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loader = findViewById(R.id.loader)
        findViewById<Button>(R.id.loginBtn).setOnClickListener{
            this.login()
        }
    }
    private fun moveToMain() {
        startActivity(Intent(this, DashboardActivity::class.java))
        animateToRight()
        finish()
    }

    private fun showLoader() {
        loader.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        loader.visibility = View.GONE
    }

    private fun login(){
        val username = findViewById<EditText>(R.id.username).text.toString()
        val password = findViewById<EditText>(R.id.pasword).text.toString()
        apiRequest.login(username, password)
    }

    override fun onApiRequestStart(apiAction: ApiAction) {
        super.onApiRequestStart(apiAction)
        showLoader()
    }

    override fun onApiRequestFailed(apiAction: ApiAction, t: Throwable) {
        super.onApiRequestFailed(apiAction, t)
        Log.wtf("wtf",t.localizedMessage)
        var res = "java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 2 column 1 path \$"
        if(t.localizedMessage.equals(res)){
            Toast.makeText(this, "Check for incorrect credentials!", Toast.LENGTH_SHORT).show()
        }
        hideLoader()
    }

    override fun onApiRequestSuccess(apiAction: ApiAction, result: Any) {
        super.onApiRequestSuccess(apiAction, result)
        when (apiAction) {
            ApiAction.LOGIN -> {
                val userResponse = result as UserResponse
                PrefsUtil.setUser(this, userResponse)
                moveToMain()
            }

        }
        hideLoader()
    }
}