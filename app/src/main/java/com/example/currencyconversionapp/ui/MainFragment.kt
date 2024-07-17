package com.example.currencyconversionapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconversionapp.R
import com.example.currencyconversionapp.databinding.FragmentMainBinding
import com.example.currencyconversionapp.presentation.MainViewModel


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var baseCurrency = ""
    private var currency = ""
    private var amount = 0.0

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
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
        initView()
    }

    private fun initView() {
        val selectBaseCurrency = binding.currencyBaseTF

        val adapterBaseCurrency = ArrayAdapter(
            requireContext(),
            R.layout.currency_item,
            resources.getStringArray(R.array.currency)
        )
        adapterBaseCurrency.setDropDownViewResource(R.layout.currency_item)

        selectBaseCurrency.setAdapter(adapterBaseCurrency)
        selectBaseCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                baseCurrency = resources.getStringArray(R.array.currency)[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }
        val selectCurrency = binding.currencyTF
        val adapterCurrency = ArrayAdapter(
            requireContext(),
            R.layout.currency_item,
            resources.getStringArray(R.array.currency)
        )
        adapterCurrency.setDropDownViewResource(R.layout.currency_item)

        selectCurrency.setAdapter(adapterCurrency)
        selectCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currency = resources.getStringArray(R.array.currency)[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

        val button = binding.buttonConvert
        button.setOnClickListener {
            amount = binding.amountTI.text.toString().toDouble()
            viewModel.conversion(baseCurrency, currency, amount)
        }






    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}