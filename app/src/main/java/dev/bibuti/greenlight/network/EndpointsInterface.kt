package dev.bibuti.greenlight.network

import dev.bibuti.greenlight.models.Users
import retrofit2.Call
import retrofit2.http.GET

interface EndpointsInterface {

    @get:GET("/users")
    val users: Call<List<Users>>

}
