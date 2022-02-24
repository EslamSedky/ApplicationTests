package com.sedky.applicationtests.data.repository

import com.sedky.applicationtests.data.api.ApiHelper

class StationsRepository(private val apiHelper : ApiHelper) {

    suspend fun getStations() = apiHelper.getStations()

}