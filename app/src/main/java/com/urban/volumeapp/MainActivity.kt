package com.urban.volumeapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), VolumeControlView.OnVolumePowerChangeListener
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        volumeControlView.setOnVolumeChangeListener(this)

        setLinesBtn.setOnClickListener {
            val scale = scaleLinesTxt.text.toString().toIntOrNull()

            if (scale != null && scale > 0)
                volumeControlView.setVolumeScale(scale)
        }

        setVolumeBtn.setOnClickListener {
            val power = volumePwrTxt.text.toString().toIntOrNull()

            if (power != null && power > 0)
                volumeControlView.setVolumePower(power)
        }
    }

    override fun onVolumePowerChange(volume: Int)
    {
        setVolEditText(volume)
    }

    private fun setVolEditText(volPwr: Int)
    {
        volumePwrTxt.setText(volPwr.toString())
    }

    override fun onDestroy()
    {
        super.onDestroy()
    }
}