package com.mindorks.framework.mvvm.presentation.ui.main.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.antonioleiva.cleanarchitecturesample.R
import com.antonioleiva.cleanarchitecturesample.databinding.ActivityMain2Binding
import com.mindorks.framework.mvvm.presentation.ui.factory.DefaultFragmentFactoryEntryPoint
import com.mindorks.framework.mvvm.presentation.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import io.supercharge.fragmentfactoryandhilt.navigator.Navigator
import javax.inject.Inject

/*
https://blog.mindorks.com/mvvm-architecture-android-tutorial-for-beginners-step-by-step-guide

https://medium.com/supercharges-mobile-product-guide/fragmentfactory-with-dagger-and-hilt-31ee17babf73

clean architecture:
https://proandroiddev.com/kotlin-clean-architecture-1ad42fcd97fa

clean architecture better described
https://antonioleiva.com/clean-architecture-android/
 */
@AndroidEntryPoint
class MainActivity2 : AppCompatActivity() {
    @Inject
    lateinit var navigator: Navigator
    private lateinit var navHostFragment: Fragment
    private lateinit var binding: ActivityMain2Binding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val entryPoint =
            EntryPointAccessors.fromActivity(
                this, DefaultFragmentFactoryEntryPoint::class.java
            )
        supportFragmentManager.fragmentFactory = entryPoint.getFragmentFactory()

        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment =
            requireNotNull(supportFragmentManager.findFragmentById(R.id.fragmentContainer))
        val navController = navHostFragment.findNavController()
        navigator.navController = navController
    }

    override fun onBackPressed() {
        if (navHostFragment.childFragmentManager.backStackEntryCount > 0) {
            navigator.navigateBack()
        } else {
            finish()
        }
    }
}
