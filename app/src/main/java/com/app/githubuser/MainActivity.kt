package com.app.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.githubuser.databinding.ActivityMainBinding
import com.app.githubuser.fragments.UserSearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
//            add(R.id.frameContainer, HomeFragment())
            add(R.id.frameContainer, UserSearchFragment())
            commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }
}