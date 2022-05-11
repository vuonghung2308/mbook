package com.mh.mbook.di.activity

import com.mh.mbook.di.fragment.FragmentBuildersModule
import com.mh.mbook.ui.main.MainActivity
import com.mh.mbook.ui.signup.SignUpActivity
import com.mh.mbook.ui.signin.SignInActivity
import com.mh.mbook.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributeSignInActivity(): SignInActivity

    @ContributesAndroidInjector
    abstract fun contributeSignUpActivity(): SignUpActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
