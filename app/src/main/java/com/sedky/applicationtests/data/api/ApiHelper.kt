package com.sedky.applicationtests.data.api

class ApiHelper(private val apiServices: ApiServices) {

    suspend fun getStations() = apiServices.getStationBeans()

}