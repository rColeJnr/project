package com.googlepractices.budgetly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.googlepractices.budgetly.databinding.FragmentExpensesBinding
import com.googlepractices.budgetly.databinding.FragmentStatisticsBinding

class ExpensesFragment: Fragment() {

    private var _binding: FragmentExpensesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExpensesBinding.inflate(inflater, container, false)
        return binding.root
    }

}