package com.sedky.applicationtests.data.api

import com.sedky.applicationtests.utils.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

     fun getRetrofit() =
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiServices::class.java)

}