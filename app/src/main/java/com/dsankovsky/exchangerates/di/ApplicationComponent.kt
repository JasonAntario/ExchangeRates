package com.dsankovsky.exchangerates.di

import android.app.Application
import com.dsankovsky.exchangerates.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component


@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}