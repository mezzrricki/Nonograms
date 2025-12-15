package com.ski.mezyn.nonograms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.ski.mezyn.nonograms.data.repository.ProgressRepository
import com.ski.mezyn.nonograms.ui.navigation.NavGraph
import com.ski.mezyn.nonograms.ui.theme.NonogramsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize progress repository
        ProgressRepository.initialize(applicationContext)
        setContent {
            NonogramsTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}