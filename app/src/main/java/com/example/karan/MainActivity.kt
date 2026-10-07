package com.example.karan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.karan.di.AppContainer
import com.example.karan.navigation.AppNavigation
import com.example.karan.ui.theme.KaranTheme

class MainActivity : ComponentActivity() {

    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Création du conteneur de dépendances.
        appContainer = AppContainer(applicationContext)

        setContent {
            KaranTheme {

                // La navigation reçoit le Repository partagé.
                AppNavigation(
                    repository = appContainer.repository
                )
            }
        }
    }
}
