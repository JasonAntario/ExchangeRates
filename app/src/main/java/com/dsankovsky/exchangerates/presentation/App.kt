package com.dsankovsky.exchangerates.presentation

import android.app.Application
import com.dsankovsky.exchangerates.di.DaggerApplicationComponent

class App : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}