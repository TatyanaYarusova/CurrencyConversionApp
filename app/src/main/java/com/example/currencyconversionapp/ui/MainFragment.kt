package com.example.currencyconversionapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconversionapp.CurrencyConversionApp
import com.example.currencyconversionapp.R
import com.example.currencyconversionapp.databinding.FragmentMainBinding
import com.example.currencyconversionapp.domain.entity.CurrencyConversion
import com.example.currencyconversionapp.presentation.MainViewModel
import com.example.currencyconversionapp.presentation.ViewModelFactory
import com.example.currencyconversionapp.presentation.state.ErrorEvent
import com.example.currencyconversionapp.presentation.state.ScreenState
import javax.inject.Inject


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var baseCurrency = ""
    private var currency = ""
    private var amount = 0.0


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as CurrencyConversionApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, ::renderState)
    }

    private fun renderState(state: ScreenState<CurrencyConversion>) {
        when (state) {
            is ScreenState.Initial -> initView()
            is ScreenState.Content -> renderContent(state.content)
            is ScreenState.Error -> renderError(state.errorType)
            is ScreenState.Loading -> renderLoading()
        }
    }

    private fun renderLoading() {
        binding.container.visibility = View.GONE
        binding.pb.visibility = View.VISIBLE
    }

    private fun renderContent(content: CurrencyConversion) {
        addFragment(CurrencyConversionFragment.newInstance(content))
    }

    private fun renderError(errorType: ErrorEvent) {
        binding.container.visibility = View.VISIBLE
        binding.pb.visibility = View.GONE
        when(errorType) {
            is ErrorEvent.ServerError -> {}
            is ErrorEvent.NetworkError -> {}
        }
    }

    private fun initView() {
        initBaseCurrencySelect()
        initCurrencySelect()
        initButton()
    }

    private fun initBaseCurrencySelect() {
        val selectBaseCurrency = binding.currencyBaseTI
        val adapterBaseCurrency = ArrayAdapter(
            requireContext(),
            R.layout.currency_item,
            resources.getStringArray(R.array.currency)
        )
        selectBaseCurrency.setAdapter(adapterBaseCurrency)
        selectBaseCurrency.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                baseCurrency = resources.getStringArray(R.array.currency)[position]
            }
        }
    }

    private fun initCurrencySelect() {
        val selectCurrency = binding.currencyTI
        val adapterCurrency = ArrayAdapter(
            requireContext(),
            R.layout.currency_item,
            resources.getStringArray(R.array.currency)
        )

        selectCurrency.setAdapter(adapterCurrency)
        selectCurrency.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currency = resources.getStringArray(R.array.currency)[position]
            }
        }
    }

    private fun initButton() {
        val button = binding.buttonConvert
        button.setOnClickListener {
            amount = binding.amountTI.text.toString().toDouble()
            viewModel.conversion(baseCurrency, currency, amount)
        }
    }

    private fun addFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}