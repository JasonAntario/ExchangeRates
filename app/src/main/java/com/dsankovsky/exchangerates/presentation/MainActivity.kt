package com.dsankovsky.exchangerates.presentation

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsankovsky.exchangerates.R
import com.dsankovsky.exchangerates.data.mapper.Constants
import com.dsankovsky.exchangerates.databinding.ActivityMainBinding
import com.dsankovsky.exchangerates.domain.models.FilterFavorite
import com.dsankovsky.exchangerates.domain.models.FilterPopular
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[MainViewModel::class.java]
    }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val component by lazy { (application as App).component }

    private var rvAdapter: CurrencyListAdapter? = null
    private var currencyDropDownAdapter: ArrayAdapter<String>? = null
    private var filterDropDownAdapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()
        initSubscriptions()
        initFilterDropDownMenu()
    }

    private fun initRecyclerView() {
        rvAdapter = CurrencyListAdapter()
        rvAdapter?.onFavoriteClickListener = {
            viewModel.updateCurrencyInfo(it)
        }
        binding.recyclerView.adapter = rvAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_popular -> {
                    viewModel.updateFilter(FilterPopular())
                    return@setOnItemSelectedListener true
                }
                R.id.menu_favourites -> {
                    viewModel.updateFilter(FilterFavorite())
                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }

    private fun initCurrencyDropDownMenu(items: List<String>) {
        if (items.isEmpty())
            return
        if (currencyDropDownAdapter != null)
            return
        currencyDropDownAdapter = ArrayAdapter(
            this@MainActivity,
            R.layout.item_dropdown,
            items
        )
        binding.dropdownCurrencies.freezesText = false
        binding.dropdownCurrencies.setAdapter(currencyDropDownAdapter)
        binding.dropdownCurrencies.setOnItemClickListener { adapterView, view, i, l ->
            viewModel.fetchCurrencyList(items[i])
        }
    }

    private fun initFilterDropDownMenu() {
        filterDropDownAdapter = ArrayAdapter(
            this@MainActivity,
            R.layout.item_dropdown,
            Constants.filters
        )
        binding.dropdownFilter.setAdapter(filterDropDownAdapter)
        binding.dropdownFilter.setOnItemClickListener { adapterView, view, i, l ->
            Log.i(TAG, "initFilterDropDownMenu: $i")
            viewModel.updateFilterSorting(Constants.filters[i])
        }
    }

    private fun initSubscriptions() {
        lifecycle.coroutineScope.launchWhenStarted {
            viewModel.currencyList.collectLatest {
                rvAdapter?.submitList(it)
                initCurrencyDropDownMenu(it.map { item -> item.name })
                Log.i(TAG, "currencies list: ${it.size}")
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}