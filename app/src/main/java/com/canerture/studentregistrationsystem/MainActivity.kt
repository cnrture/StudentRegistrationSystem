package com.canerture.studentregistrationsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.canerture.studentregistrationsystem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            navHostFragment.navController
        )

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.administratorFragment -> binding.toolbar.title = "Administration"
                R.id.registrationFragment -> binding.toolbar.title = "Registration"
                R.id.registeredStudentsFragment -> binding.toolbar.title = "Registered Students"
                R.id.studentDetailFragment -> binding.toolbar.title = "Student Detail"
            }
        }

    }
}