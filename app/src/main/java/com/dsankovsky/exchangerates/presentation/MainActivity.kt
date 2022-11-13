package com.dsankovsky.exchangerates.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsankovsky.exchangerates.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private var adapter: CurrencyListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adapter = CurrencyListAdapter()
        adapter?.onFavoriteClickListener = {
            viewModel.updateCurrencyInfo(it)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        initSubscriptions()
    }

    private fun initSubscriptions() {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED){
//                viewModel.stateF.collect{
//                    adapter?.submitList(it)
//                }
//            }
//        }

        lifecycle.coroutineScope.launchWhenStarted {
            viewModel.currencyList.collectLatest {
                adapter?.submitList(it)
                Log.i(TAG, "currencies list: $it")
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}