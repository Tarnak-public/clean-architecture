package com.antonioleiva.cleanarchitecturesample.framework

import com.antonioleiva.data.repository.DeviceLocationSource
import com.antonioleiva.domain.Location
import java.util.*

class FakeLocationSource : DeviceLocationSource {

    private val random = Random(System.currentTimeMillis())

    override fun getDeviceLocation(): Location =
        Location(random.nextDouble() * 180 - 90, random.nextDouble() * 360 - 180, Date())
}