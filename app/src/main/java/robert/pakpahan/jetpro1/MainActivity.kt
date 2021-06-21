package robert.pakpahan.jetpro1

import android.os.Bundle
import android.view.Menu
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import robert.pakpahan.jetpro1.R.*
import robert.pakpahan.jetpro1.base.BaseActivity
import robert.pakpahan.jetpro1.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutActivity: Int = layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        val navController = Navigation.findNavController(this, id.nav_host_main_fragment)
        setupWithNavController(binding.bottomNavigationView, navController)
        setupActionBarWithNavController(
            navController,
            AppBarConfiguration.Builder(id.fragment_film, id.fragment_tv, id.fragment_favorite).build()
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return true
    }
}