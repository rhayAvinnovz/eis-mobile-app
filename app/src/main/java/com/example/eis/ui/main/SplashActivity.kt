package com.example.eis.ui.main

import android.content.Intent
import android.os.Bundle
import com.example.eis.R
import com.example.eis.ui.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {
    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashcreen)
        this.loadResources()

    }

    private fun loadResources() {
        val disposable = Observable.interval(5000L, TimeUnit.MILLISECONDS)
            .timeInterval()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                this.moveToLogin()
            }
        compositeDisposable.add(disposable)
    }

    private fun moveToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        animateToRight()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose();
    }
}