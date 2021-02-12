package com.example.android.roomwordsample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.example.android.roomwordsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val preferenceViewModel: PreferenceViewModel by viewModels {
        PreferenceViewModelFactory((this.application as WordsApplication).prefManager)
    }

    private var item: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)

        item = menu.findItem(R.id.app_bar_switch)

        item?.setActionView(R.layout.switch_item)

        item?.actionView?.findViewById<SwitchCompat>(R.id.switch_button)
            ?.setOnCheckedChangeListener { _, isChecked ->
                when (isChecked) {
                    false -> {
                        preferenceViewModel.onDarkModeClick(UiTheme.LIGHT)
                    }
                    true -> {
                        preferenceViewModel.onDarkModeClick(UiTheme.DARK)
                    }
                }
            }

        observeUIPreferences()
        return true
    }

    private fun observeUIPreferences() {
        preferenceViewModel.preferencesFlow.observe(this) { uiTheme ->
            when (uiTheme!!) {
                UiTheme.LIGHT -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    item?.actionView?.findViewById<SwitchCompat>(R.id.switch_button)?.isChecked =
                        false
                }
                UiTheme.DARK -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    item?.actionView?.findViewById<SwitchCompat>(R.id.switch_button)?.isChecked =
                        true
                }
            }
        }
    }

}
