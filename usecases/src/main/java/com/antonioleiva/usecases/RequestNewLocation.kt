package com.antonioleiva.usecases

import com.antonioleiva.data.repository.LocationsRepository
import com.antonioleiva.domain.Location

class RequestNewLocation(private val locationsRepository: LocationsRepository) {

    operator fun invoke(): List<Location> = locationsRepository.requestNewLocation()

}
