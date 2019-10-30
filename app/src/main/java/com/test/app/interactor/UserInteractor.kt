package com.test.app.interactor

import com.test.app.model.Album
import com.test.app.model.InteractorCallBack
import com.test.app.model.User
import com.test.app.network.UserAlbumApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserInteractor(val userAlbumApi: UserAlbumApi) {

    // TODO Replace this callback by RxJava in future.

    fun fetchUsers(interactorCallBack: InteractorCallBack<List<User>?>) {
        userAlbumApi.fetchUsers().enqueue(
            object : Callback<List<User>?> {
                override fun onResponse(call: Call<List<User>?>, response: Response<List<User>?>) {
                    interactorCallBack.onSuccess(response.body())
                }

                override fun onFailure(call: Call<List<User>?>, t: Throwable) {
                    interactorCallBack.onFailure(t)
                }
            }
        )
    }

    fun fetchAlbum(id: String?, interactorCallBack: InteractorCallBack<List<Album>?>) {
        userAlbumApi.fetchAlbumForId(id).enqueue(
            object : Callback<List<Album>?> {

                override fun onResponse(
                    call: Call<List<Album>?>,
                    response: Response<List<Album>?>
                ) {
                    interactorCallBack.onSuccess(response.body())
                }

                override fun onFailure(call: Call<List<Album>?>, t: Throwable) {
                    interactorCallBack.onFailure(t)
                }
            }
        )
    }
}
