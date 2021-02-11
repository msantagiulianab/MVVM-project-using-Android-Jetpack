package com.example.android.roomwordsample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.android.roomwordsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var settingDataStore: SettingDataStore

    private var isDarkMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        settingDataStore = SettingDataStore(this)


//        observeUiPreferences()
//        initViews()

    }


//    private fun initViews() {
////        val buttonLight = menuInflater.inflate(R.menu.menu_item).findViewById(R.id.light_selection, applicationContext)
//
////            .setOnClickListener {
////                lifecycleScope.launch {
////                    when (isDarkMode) {
////                        true -> settingDataStore.setDarkMode(UiMode.LIGHT)
////                        false -> settingDataStore.setDarkMode(UiMode.DARK)
////                    }
////                }
////            }
//    }
//
//    private fun observeUiPreferences() {
//        settingDataStore.uiModeFlow.asLiveData().observe(this) { uiMode ->
//            when (uiMode) {
//                UiMode.LIGHT -> onLightMode()
//                UiMode.DARK -> onDarkMode()
//            }
//        }
//    }
//
//    private fun onLightMode() {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        isDarkMode = false
//
//    }
//
//    private fun onDarkMode() {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        isDarkMode = true
//    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.light_selection -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                isDarkMode = false
            }
            R.id.dark_selection -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                isDarkMode = true
            }
        }
        return isDarkMode
    }

}
