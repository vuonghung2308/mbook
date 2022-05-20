package com.mh.mbook

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.google.firebase.messaging.FirebaseMessaging
import com.mh.mbook.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject


class App : Application(), HasAndroidInjector {
    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var sp: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppInjector.init(this)
        createNotificationChanel()

        runBlocking(Dispatchers.IO) {
            val token = sp.getString("token", null)
            token ?: let {
                val fm = FirebaseMessaging.getInstance()
                fm.token.addOnCompleteListener {
                    sp.edit().putString("token", it.result)
                        .commit()
                    println(it.result)
                }
            }
        }
    }

    private fun createNotificationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(Context.NOTIFICATION_SERVICE)
            manager as NotificationManager
            val id = getString(R.string.channel_id)
            val name = getString(R.string.channel_name)
            val important = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, important)
            manager.createNotificationChannel(channel)
        }
    }

    override fun androidInjector() = injector
}
