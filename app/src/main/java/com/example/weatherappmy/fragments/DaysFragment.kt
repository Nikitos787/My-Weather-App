package com.example.weatherappmy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherappmy.MainViewModel
import com.example.weatherappmy.adapters.DaysAdapter
import com.example.weatherappmy.databinding.FragmentDaysBinding

class DaysFragment : Fragment() {
    private lateinit var binding: FragmentDaysBinding
    private lateinit var adapter: DaysAdapter
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        model.response.observe(viewLifecycleOwner) {
            adapter.submitList(it?.forecast?.forecastday)
        }
    }

    private fun init() = with(binding) {
        adapter = DaysAdapter()
        recyclerViewDays.layoutManager = LinearLayoutManager(activity)
        recyclerViewDays.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
    }
}
