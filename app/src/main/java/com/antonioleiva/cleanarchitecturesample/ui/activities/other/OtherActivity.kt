package com.antonioleiva.cleanarchitecturesample.ui.activities.other

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.antonioleiva.cleanarchitecturesample.R
import com.antonioleiva.cleanarchitecturesample.databinding.ActivityOtherBinding
import com.antonioleiva.cleanarchitecturesample.ui.activities.other.viewmodel.OtherViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import com.antonioleiva.cleanarchitecturesample.ui.activities.other.navigator.Navigator
import com.antonioleiva.cleanarchitecturesample.ui.factory.DefaultFragmentFactoryEntryPoint
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
class OtherActivity : AppCompatActivity() {
    private val tagDebug: String = "fetchData()"
    @Inject
    lateinit var navigator: Navigator
    private lateinit var navHostFragment: Fragment
    private lateinit var binding: ActivityOtherBinding
    private val otherViewModel: OtherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val entryPoint =
            EntryPointAccessors.fromActivity(
                this, DefaultFragmentFactoryEntryPoint::class.java
            )
        supportFragmentManager.fragmentFactory = entryPoint.getFragmentFactory()

        super.onCreate(savedInstanceState)
        binding = ActivityOtherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(tagDebug, "OtherActivity onCreate() ")

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
