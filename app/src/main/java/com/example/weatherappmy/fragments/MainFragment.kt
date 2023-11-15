package com.example.weatherappmy.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.example.weatherappmy.DialogManager
import com.example.weatherappmy.MainViewModel
import com.example.weatherappmy.adapters.ViewPagerAdapter
import com.example.weatherappmy.databinding.FragmentMainBinding
import com.example.weatherappmy.isPermissionGranted
import com.example.weatherappmy.model.WeatherForecastDay
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import org.json.JSONObject

class MainFragment : Fragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val listOfFragments =
        listOf(HoursFragment.newInstance(), DaysFragment.newInstance())
    private val tabList = listOf("Hours", "Days")
    private lateinit var pLauncher: ActivityResultLauncher<String> // для спроса разрешения у юзера
    private lateinit var binding: FragmentMainBinding
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        init()
        updateCurrentCard()

    }

    override fun onResume() {
        super.onResume()
        checkLocation()
    }

    private fun updateCurrentCard() = with(binding) {
        model.response.observe(viewLifecycleOwner) {
            val maxMinTemp =
                "${it.forecast.forecastday[0].day.maxtemp_c.toFloat().toInt()}℃ / ${it.forecast.forecastday[0].day.mintemp_c.toFloat().toInt()}℃"
            val currentTemperatureFormatted = "${it.current.temp_c.toFloat().toInt()} ℃"
            textViewDate.text = it.location.localtime
            textViewCity.text = it.location.name
            textViewCondition.text = it.current.condition.text
            textViewCurrentTemeprature.text = currentTemperatureFormatted.ifEmpty { maxMinTemp }
            Picasso.get().load("https:" + it.current.condition.icon).into(imageViewWeather)
        }
    }

    private fun init() = with(binding) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val adapter = ViewPagerAdapter(activity as FragmentActivity, listOfFragments)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabList[position]
        }.attach()
        imageButtonSync.setOnClickListener {
            tabLayout.selectTab(tabLayout.getTabAt(0))
            checkLocation()
        }
        imageButtonSearch.setOnClickListener {
            DialogManager.searchByName(requireContext(), object : DialogManager.Listener {
                override fun onClick(name: String?) {
                    name?.let { it1 -> model.updateWeatherData(it1) }
                }
            })
        }
        model.error.observe(viewLifecycleOwner) {
            if (!it.isNullOrBlank()) {
                DialogManager.errorMessage(requireContext(), it)
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun checkLocation() {
        if (isLocationEnabled()) {
            getLocation()
        } else {
            DialogManager.locationSettingDialog(requireContext(), object : DialogManager.Listener {
                override fun onClick(name: String?) {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }

            })
        }
    }

    private fun getLocation() {
        val token = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, token.token)
            .addOnCompleteListener {
                model.updateWeatherData("${it.result.latitude},${it.result.longitude}")
            }
    }

    private fun permissionListener() {
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(activity, "Permission: is $it", Toast.LENGTH_LONG).show()
        }
    } // register this listener for asking permission

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

//    private fun registerWeatherData(city: String) {
//        val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
//                API_KEY +
//                "&q=" +
//                city +
//                "&days=3&aqi=no&alerts=no"
//
//        val queue = Volley.newRequestQueue(context)
//        val request = StringRequest(
//            Request.Method.GET,
//            url,
//            { result ->
//                parseResult(result)
//            },
//            { error ->
//                Log.d("MyLog", "$error")
//            }
//        )
//        queue.add(request)
//    }

//    private fun parseResult(result: String) {
//        val mainObject = JSONObject(result)
//        val list = parseDays(mainObject)
//        parseCurrentData(mainObject, list[0])
//        Log.d("MyLog", "$result")
//    }

//    private fun parseCurrentData(mainObject: JSONObject, weatherItem: WeatherModel) {
//        val item = WeatherModel(
//            mainObject.getJSONObject("location").getString("name"),
//            mainObject.getJSONObject("current").getString("last_updated"),
//            mainObject.getJSONObject("current").getJSONObject("condition").getString("text"),
//            mainObject.getJSONObject("current").getString("temp_c"),
//            weatherItem.maxTemp,
//            weatherItem.minTemp,
//            mainObject.getJSONObject("current").getJSONObject("condition").getString("icon"),
//            weatherItem.hours
//        )
//        model.liveDataCurrent.value = item
//    }

//    private fun parseDays(mainObject: JSONObject): List<WeatherForecastDay> {
//        val list = ArrayList<WeatherForecastDay>()
//        val daysArray = model.response.value?.forecast?.forecastday
//        val cityName = model.response.value.location.name
//
//        for (i in 0 until daysArray.length()) {
//            val day = daysArray[i] as JSONObject
//            val item = WeatherModel(
//                cityName,
//                day.getString("date"),
//                day.getJSONObject("day").getJSONObject("condition").getString("text"),
//                "",
//                day.getJSONObject("day").getString("maxtemp_c").toFloat().toInt().toString(),
//                day.getJSONObject("day").getString("mintemp_c").toFloat().toInt().toString(),
//                day.getJSONObject("day").getJSONObject("condition").getString("icon"),
//                day.getJSONArray("hour").toString()
//            )
//            list.add(item)
//        }
//        model.liveDataList.value = list
//        return list
//    }


    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
