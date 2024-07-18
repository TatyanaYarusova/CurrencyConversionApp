package com.example.currencyconversionapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.currencyconversionapp.data.mapper.CurrencyConversionParcelableModel
import com.example.currencyconversionapp.data.mapper.toEntity
import com.example.currencyconversionapp.data.mapper.toParcelableModel
import com.example.currencyconversionapp.databinding.FragmentCurrencyConvertionBinding
import com.example.currencyconversionapp.domain.entity.CurrencyConversion

class CurrencyConversionFragment : Fragment() {

    private var _currencyConversion: CurrencyConversion? = null
    private val currencyConversion
        get() = _currencyConversion ?: throw RuntimeException("Currency is null!")

    private var _binding: FragmentCurrencyConvertionBinding? = null
    private val binding get() = _binding!!

    private fun parseArguments() {
        val args = requireArguments()
        if (args.containsKey(CURRENCY)) {
            _currencyConversion = (args.getParcelable(CURRENCY) as? CurrencyConversionParcelableModel)?.toEntity()
        } else {
            throw RuntimeException("Currency conversion argument is absent")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyConvertionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.fromAmountText.text = currencyConversion.amount.toString()
        binding.fromCodeText.text = currencyConversion.from
        binding.toAmountText.text = currencyConversion.result.toString()
        binding.toCodeText.text = currencyConversion.to
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(currency: CurrencyConversion) = CurrencyConversionFragment().apply {
            arguments = Bundle().apply {
                putParcelable(CURRENCY, currency.toParcelableModel())
            }
        }

        private const val CURRENCY = "extra_currency_conversion"
    }
}