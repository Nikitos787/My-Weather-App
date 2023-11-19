package com.example.weatherappmy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherappmy.MainViewModel
import com.example.weatherappmy.adapters.HoursAdapter
import com.example.weatherappmy.databinding.FragmentHoursBinding


class HoursFragment : Fragment() {
    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: HoursAdapter
    private val model : MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
        model.response.observe(viewLifecycleOwner) {
            adapter.submitList(it?.forecast?.forecastday?.get(0)?.hour)
        }
    }

    private fun initRecycleView() = with(binding) {
        recycleViewHours.layoutManager = LinearLayoutManager(activity)
        adapter = HoursAdapter()
        recycleViewHours.adapter = adapter

    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}
