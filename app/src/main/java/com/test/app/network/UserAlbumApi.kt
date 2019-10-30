package com.test.app.network

import com.test.app.model.Album
import com.test.app.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserAlbumApi {

    @GET("/users")
    fun fetchUsers(): Call<List<User>?>

    @GET("/photos")
    fun fetchAlbumForId(@Query("albumId") id: String?): Call<List<Album>?>
}
