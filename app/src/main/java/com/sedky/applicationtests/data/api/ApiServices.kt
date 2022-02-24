package com.sedky.applicationtests.data.api

import com.sedky.applicationtests.data.model.UserList
import retrofit2.http.GET

interface ApiServices {

    @GET("api/users")
    suspend fun getStationBeans(): UserList

}