package com.tazarki.weather.presentation.fragments.weather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.tazarki.domain.common.BaseResult
import com.tazarki.domain.weather.dto.WeatherInfo
import com.tazarki.domain.weather.dto.toWeatherInfo
import com.tazarki.weather.R
import com.tazarki.weather.databinding.FragmentWeatherBinding
import com.tazarki.weather.presentation.fragments.base.BaseFragment
import com.tazarki.weather.presentation.fragments.weather.adapters.WeatherInfoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class WeatherFragment : BaseFragment<FragmentWeatherBinding>(FragmentWeatherBinding::inflate) {

    private val weatherViewModel : WeatherViewModel by activityViewModels()

    @Inject
    lateinit var weatherInfoAdapter: WeatherInfoAdapter
    private var items = ArrayList<WeatherInfo>()

    var cityIndex = 0
    var msgIndex = 0

    private val cities = arrayListOf("Rennes", "Paris", "Nantes", "Bordeaux", "Lyon")
    private var messages = arrayOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messages = requireContext().resources.getStringArray(R.array.waiting_list)

        initViews()

        collectData()

    }

    // Collect progress in seconds, update progressBar, call getWeather and showWaitingMessages
    private fun collectProgress(){
        // We start our progressBar
        weatherViewModel.updateProgress()

        // Collect the flow updates (progress in seconds)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            weatherViewModel.progress.collectLatest {

                // Update the progressBar UI
                updateProgressBar(it)

                // Send the current progress to decide when to call the weather api with the appropriate city
                getWeatherInfo(it)

                // Send the current progress to decide which waiting message should be shown to the user
                showWaitingMessages(it)


            }
        }
    }

    // We collect the weather data for the city and add it to our list and make ui updates depending on the state
    private fun collectWeather(){
        lifecycleScope.launchWhenStarted {
            weatherViewModel.weatherData.collectLatest {
                when(it){
                    is BaseResult.Loading -> {
                        //Here we could show a loading screen for the user while waiting for the api response
                    }
                    is BaseResult.Success -> {
                        //We successfully received the data
                        //We fill the list with the needed info using a mapper
                        items.add(it.data.toWeatherInfo())
                    }
                    is BaseResult.ErrorUnknown -> {
                        /*

                        * In case of an error, which could be any of the following:
                        - Internet problem
                        - Server problem
                        - ...

                        * These exceptions could and should be handled but I couldn't due to the lack of time.

                         */
                    }
                    else -> {
                        // Usually we wouldn't need an else block, because we should handle every state independently
                    }
                }
            }
        }
    }

    // Here we call the collect data from the progress and the weather data for each city
    private fun collectData(){

        collectProgress()

        collectWeather()
    }

    // Here I use "when" for readability and conciseness instead of if-else blocks
    // Depending on the current progress we decide which city to fetch its weather
    // The index refers to the city name in the cities list like below :
    /*
        0 -> Rennes
        1 -> Paris
        2 -> Nantes
        3 -> Bordeaux
        4 -> Lyon
     */
    // When we reach 60 seconds and the progressBar is full, we show the result
    private fun getWeatherInfo(progress: Int){

        when(progress) {
            0 -> {
                weatherViewModel.getWeatherByCityName(cities[cityIndex])
                cityIndex++
            }
            10 -> {
                weatherViewModel.getWeatherByCityName(cities[cityIndex])
                cityIndex++
            }
            20 -> {
                weatherViewModel.getWeatherByCityName(cities[cityIndex])
                cityIndex++
            }
            30 -> {
                weatherViewModel.getWeatherByCityName(cities[cityIndex])
                cityIndex++
            }
            40 -> {
                weatherViewModel.getWeatherByCityName(cities[cityIndex])
            }
            60 -> {
                showResult()
            }

        }
    }

    // Update the progressBar UI
    private fun updateProgressBar(progress: Int){
        binding.progressBar.progress = progress
    }

    // Restart the process if the button is clicked
    private fun initViews(){
        binding.btnRestart.setOnClickListener {
            restart()
        }
    }

    // Here we need to initialize the indexes in case of restarting the process, otherwise we would get ArrayIndexOutOfBoundsException
    private fun initIndexes(){

        cityIndex = 0
        msgIndex = 0
    }

    // We set our adapter with the data we want to show in the recyclerView
    private fun setAdapter(list: ArrayList<WeatherInfo>){

        weatherInfoAdapter.setData(list)
        binding.rvWeather.apply {
            adapter = weatherInfoAdapter
        }
    }

    // Here we do some UI handling to show the result and Restart button but also hide the progressBar along with the waiting messages
    private fun showResult(){

        setAdapter(items)
        binding.clContainer.visibility = View.VISIBLE
        binding.tvProgress.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.btnRestart.visibility = View.VISIBLE
    }

    // We handle the messages to be shown every 6 seconds
    // Also iterate between the list of messages to show a different 1 every time
    private fun showWaitingMessages(progress: Int){

        if (progress % 6 == 0 && progress < 60){
            if (msgIndex < messages.size){
                binding.tvProgress.text = messages[msgIndex]
                msgIndex++
            }else{
                msgIndex = 0
                binding.tvProgress.text = messages[msgIndex]
                msgIndex++
            }

        }
    }
    // When we restart the process we need to also reset the views to their default state
    private fun resetViewsToDefault(){
        binding.clContainer.visibility = View.GONE
        binding.tvProgress.visibility = View.VISIBLE
        binding.progressBar.visibility = View.VISIBLE
        binding.btnRestart.visibility = View.GONE
    }

    // We need to clear the list when we restart the process.
    // Also reset the indexes
    // Reset the views
    // Recall the progress
    private fun restart(){
        items.clear()
        initIndexes()
        resetViewsToDefault()
        weatherViewModel.updateProgress()
    }

}