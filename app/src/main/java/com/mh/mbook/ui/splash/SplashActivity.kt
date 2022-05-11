package com.mh.mbook.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mh.mbook.di.Injectable
import com.mh.mbook.repository.UserRepository
import com.mh.mbook.ui.main.MainActivity
import com.mh.mbook.ui.signin.SignInActivity
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), Injectable {
    @Inject
    lateinit var repo: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repo.loadUser().observe(this) {
            if(it == null) {
                Intent(this, SignInActivity::class.java)
                    .apply { startActivity(this) }
            } else {
                Intent(this, MainActivity::class.java)
                    .apply { startActivity(this) }
            }
            finish()
        }
    }
}