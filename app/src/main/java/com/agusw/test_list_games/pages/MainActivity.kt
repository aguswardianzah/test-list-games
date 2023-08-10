package com.agusw.test_list_games.pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.agusw.test_list_games.R
import com.agusw.test_list_games.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val layout by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navController by lazy { findNavController(R.id.page_container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layout.root)
        setSupportActionBar(layout.toolbar)
        layout.toolbar.title = "Games For You"

        layout.menuBot.setOnItemSelectedListener {
            if (it.itemId == R.id.home_menu) {
                navController.navigate(R.id.action_global_listGameFragment)
                layout.toolbar.title = "Games For You"
            } else {
                navController.navigate(R.id.action_global_favGameFragment)
                layout.toolbar.title = "Favorite Games"
            }

            true
        }
    }
}