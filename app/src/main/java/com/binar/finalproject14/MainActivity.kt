package com.binar.finalproject14

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.binar.finalproject14.databinding.ActivityMainBinding
import com.binar.finalproject14.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationHome.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    Navigation.findNavController(this,R.id.fragmentContainer).navigate(R.id.homeFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.pastFragment -> {
                    Navigation.findNavController(this,R.id.fragmentContainer).navigate(R.id.pastFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.whislistFragment -> {
                    Navigation.findNavController(this,R.id.fragmentContainer).navigate(R.id.whislistFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profileFragment -> {
                    Navigation.findNavController(this,R.id.fragmentContainer).navigate(R.id.profileFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }
}