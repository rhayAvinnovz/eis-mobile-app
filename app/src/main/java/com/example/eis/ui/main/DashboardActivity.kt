package com.example.eis.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.eis.R
import com.example.eis.ui.base.BaseActivity
import com.example.eis.ui.utils.PrefsUtil
import com.google.android.material.navigation.NavigationView

class DashboardActivity : BaseActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var welcome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        initDrawer()
        initFunctions()
    }

    private fun initDrawer() {
        toolbar = findViewById(R.id.toolBar)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawerLayout)
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout,toolbar, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarToggle.syncState()
        navView = findViewById(R.id.nav_view)
        val headerView = navView.getHeaderView(0)
        val user = PrefsUtil.getUser(this)
        headerView.findViewById<TextView>(R.id.username).text = "${user?.firstname} ${user?.lastname}"
        headerView.findViewById<TextView>(R.id.position).text = "${user?.position}"
        headerView.findViewById<TextView>(R.id.department).text = "${user?.department}"
        welcome = findViewById(R.id.welcome_name)
        welcome.text = "Welcome back ${user?.username}"
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout -> {
                    PrefsUtil.clear(this)
                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    animateToLeft()
                    finish()
                    true
                }
                else -> false
            }
        }
    }
    private fun initFunctions(){
        findViewById<Button>(R.id.btn_stationary).setOnClickListener{
            startActivity(Intent(this,StationaryActivity::class.java))
            animateToRight()
            finish()
        }

        findViewById<Button>(R.id.btn_mobile).setOnClickListener{
            startActivity(Intent(this,MobileActivity::class.java))
            animateToRight()
            finish()
        }

        findViewById<Button>(R.id.btn_area).setOnClickListener{
            startActivity(Intent(this,AreaActivity::class.java))
            animateToRight()
            finish()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        if (drawerLayout.isDrawerOpen(navView)) {
            drawerLayout.closeDrawer(navView)
        } else {
            drawerLayout.openDrawer(navView)
        }
        return true
    }
    override fun onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}