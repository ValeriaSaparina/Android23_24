package com.example.homework6.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.homework6.BuildConfig
import com.example.homework6.R
import com.example.homework6.base.Constants
import com.example.homework6.databinding.FragmentWeatherBinding
import com.example.homework6.ui.base.BaseFragment
import com.example.homework6.ui.debug.DebugFragment
import com.example.homework6.ui.weather.model.WeatherUiModel
import com.squareup.picasso.Picasso

class WeatherFragment : BaseFragment() {

    private var viewBinding: FragmentWeatherBinding? = null
    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModel.Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentWeatherBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        observers()

    }

    private fun initListener() {
        viewBinding?.run {
            loadBtn.setOnClickListener {
                if (!viewModel.loadingWeather.value) {
                    viewModel.onLoadWeatherClicked(getCity())
                } else {
                    showToast(getString(R.string.loading_wait))
                }
            }
            if (BuildConfig.DEBUG) {
                root.setOnTouchListener(customTouchListener)
            }
        }
    }

    private fun getCity(): String {
        return viewBinding?.cityInputLayout?.editText?.text.toString()
    }


    private val customTouchListener = object : View.OnTouchListener {
        private var touchCount = 0
        private var startTime: Long = 0L

        override fun onTouch(view: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (touchCount == 0) {
                        startTime = System.currentTimeMillis()
                    } else {
                        val newTime = System.currentTimeMillis()
                        if (newTime - startTime > Constants.TOUCH_LIMIT_TIME) {
                            startTime = newTime
                            touchCount = 0
                        }
                    }
                    touchCount++
                    if (touchCount >= Constants.TOUCH_THRESHOLD) {
                        parentFragmentManager.beginTransaction().addToBackStack(null)
                            .replace(R.id.container, DebugFragment()).commit()
                        touchCount = 0
                    }
                }
            }
            return true
        }
    }

    private fun observers() {

        with(viewModel) {
            weatherUiModel.observe {
                if (it != null) {
                    showData(it)
                }
            }

            loadingWeather.observe {
                setVisibilityWeatherData(!it && weatherUiModel.value != null)
                setVisibilityProgressBar(it)
            }

            error.observe {
                if (it != null) {
                    showToast(it.message ?: "")
                }
            }
        }
    }

    private fun setVisibilityProgressBar(flag: Boolean) {
        viewBinding?.progressBar?.visibility = if (flag) View.VISIBLE else View.GONE
    }

    private fun setVisibilityWeatherData(flag: Boolean = false) {
        viewBinding?.weatherDataGroup?.visibility = if (flag) View.VISIBLE else View.GONE
    }

    private fun showData(weatherModel: WeatherUiModel) {
        viewBinding?.run {
            with(currentWeatherTv) {
                text = getString(R.string.current_weather_for, getCity())
                visibility = View.VISIBLE
            }
        }
        weatherModel.mainData?.temp?.let { showTemp(it) }
        weatherModel.weatherIconData?.srcImg?.let { showIcon(it) }
    }

    private fun showTemp(temp: Float) {
        viewBinding?.run {
            tempTv.text = getString(R.string.temp, temp.toString())
            weatherDataGroup.visibility = View.VISIBLE
        }
    }

    private fun showIcon(src: String) {
        viewBinding?.run {
            Picasso.get().load("${BuildConfig.OPEN_WEATHER_IMG_BASE_URL}${src}@2x.png")
                .into(weatherIconIv)
        }
    }


}