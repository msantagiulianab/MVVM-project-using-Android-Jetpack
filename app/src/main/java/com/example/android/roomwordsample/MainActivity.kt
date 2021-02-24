package com.example.android.roomwordsample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.android.roomwordsample.application.WordsApplication
import com.example.android.roomwordsample.databinding.ActivityMainBinding
import com.example.android.roomwordsample.preferences.UiTheme

class MainActivity : AppCompatActivity() {

    private val preferenceViewModel: PreferenceViewModel by viewModels {
        PreferenceViewModelFactory((this.application as WordsApplication).prefManager)
    }

    private var item: MenuItem? = null

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.mainFragment)
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.my_nav_host_fragment).navigateUp(appBarConfiguration)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.my_nav_host_fragment))
                || super.onOptionsItemSelected(item)
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
