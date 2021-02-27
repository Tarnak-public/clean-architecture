package com.antonioleiva.cleanarchitecturesample.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.antonioleiva.cleanarchitecturesample.databinding.ActivityMainBinding
import com.antonioleiva.cleanarchitecturesample.databinding.ViewLocationItemBinding
import com.antonioleiva.cleanarchitecturesample.framework.FakeLocationSource
import com.antonioleiva.cleanarchitecturesample.framework.InMemoryLocationPersistenceSource
import com.antonioleiva.cleanarchitecturesample.ui.main.view.OtherActivity
import com.antonioleiva.cleanarchitecturesample.ui.main.viewmodel.StudentViewModel
import com.antonioleiva.data.repository.LocationsRepository
import com.antonioleiva.usecases.GetLocations
import com.antonioleiva.usecases.RequestNewLocation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainPresenter.View {
    private val tagDebug: String = "fetchData()"
//    private val studentViewModel: StudentViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewLocationItemBinding: ViewLocationItemBinding
    private val locationsAdapter = LocationsAdapter()
    private val presenter: MainPresenter


    init {

        // This would be done by a dependency injector in a complex App
        val persistence = InMemoryLocationPersistenceSource()
        val deviceLocation = FakeLocationSource()
        val locationsRepository = LocationsRepository(persistence, deviceLocation)
        presenter = MainPresenter(
            this,
            GetLocations(locationsRepository),
            RequestNewLocation(locationsRepository)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewLocationItemBinding = ViewLocationItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(tagDebug, "MainActivity STARTED ")
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = locationsAdapter
        }
//        recycler.adapter = locationsAdapter

        binding.newLocationBtn.setOnClickListener { presenter.newLocationClicked() }

        binding.startActivityBtn.setOnClickListener {
            val intent = Intent(this, OtherActivity::class.java)
            startActivity(intent)
        }
        presenter.onCreate()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun renderLocations(locations: List<Location>) {
        locationsAdapter.items = locations
    }
}