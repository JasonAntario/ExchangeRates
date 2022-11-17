package com.dsankovsky.exchangerates.di

import androidx.lifecycle.ViewModel
import com.dsankovsky.exchangerates.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindViewModel(viewModel: MainViewModel): ViewModel
}