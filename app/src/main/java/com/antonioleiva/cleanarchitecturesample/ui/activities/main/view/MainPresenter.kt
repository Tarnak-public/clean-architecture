package com.antonioleiva.cleanarchitecturesample.ui.activities.main.view


import com.antonioleiva.cleanarchitecturesample.utils.Location
import com.antonioleiva.cleanarchitecturesample.utils.toPresentationModel
import com.antonioleiva.usecases.GetLocations
import com.antonioleiva.usecases.RequestNewLocation
import dagger.hilt.android.scopes.ViewScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.antonioleiva.domain.Location as DomainLocation

@ViewScoped
class MainPresenter(
    private var view: View?,
    private val getLocations: GetLocations,
    private val requestNewLocation: RequestNewLocation
) {
    interface View {
        fun renderLocations(locations: List<Location>)
    }

    fun onCreate() = GlobalScope.launch(Dispatchers.Main) {
        val locations = withContext(Dispatchers.IO) { getLocations() }
        view?.renderLocations(locations.map(DomainLocation::toPresentationModel))
    }

    fun newLocationClicked() = GlobalScope.launch(Dispatchers.Main) {
        val locations = withContext(Dispatchers.IO) { requestNewLocation() }
        view?.renderLocations(locations.map(DomainLocation::toPresentationModel))
    }

    fun onDestroy() {
        view = null
    }
}