package com.ancafarcas.proiectandroid.features.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ancafarcas.proiectandroid.R
import com.ancafarcas.proiectandroid.databinding.ActivityHomeBinding
import com.ancafarcas.proiectandroid.features.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.homeContainerView) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration= AppBarConfiguration(navController.graph)
        setSupportActionBar(binding.topAppBar)

        setupActionBarWithNavController(navController, appBarConfiguration)


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater

        inflater.inflate(R.menu.menu_home_actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController.popBackStack()
                return true
            }
            R.id.action_logout -> {
                Firebase.auth.signOut()
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}