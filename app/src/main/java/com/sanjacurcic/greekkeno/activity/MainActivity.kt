package com.sanjacurcic.greekkeno.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.sanjacurcic.greekkeno.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        navController = binding.navHostContainer.getFragment<NavHostFragment>().navController
        setNavigation()
        setContentView(binding.root)
    }

    private fun setNavigation() {
        val navGraph = navController.navInflater.inflate(com.sanjacurcic.navigation.R.navigation.main_nav_graph)
        navGraph.setStartDestination(com.sanjacurcic.navigation.R.id.game_nav_graph)
        navController.graph = navGraph
    }
}