package com.test.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.app.interactor.UserInteractor
import com.test.app.model.Album
import com.test.app.model.InteractorCallBack
import com.test.app.model.User
import javax.inject.Inject

class UserViewModel @Inject constructor(val userInteractor: UserInteractor) : ViewModel() {

    private val showLoadingProgressLiveData = MutableLiveData<Boolean>()
    val loadingProgressDialogObservable: LiveData<Boolean>
        get() = showLoadingProgressLiveData

    private val errorProgressLiveData = MutableLiveData<String>()
    val errorObservable: LiveData<String>
        get() = errorProgressLiveData

    private val userListLiveData = MutableLiveData<List<User>>()
    val userListObservable: LiveData<List<User>>
        get() = userListLiveData

    private val albumListLiveData = MutableLiveData<List<Album>>()
    val albumListObservable: LiveData<List<Album>>
        get() = albumListLiveData


    fun fetchUserList() {
        showLoadingProgressLiveData.postValue(true)
        userInteractor.fetchUsers(object : InteractorCallBack<List<User>?> {
            override fun onSuccess(response: List<User>?) {
                showLoadingProgressLiveData.postValue(false)
                userListLiveData.postValue(response)
            }

            override fun onFailure(throwable: Throwable) {
                showLoadingProgressLiveData.postValue(false)
                errorProgressLiveData.postValue(null)
            }
        })
    }

    fun fetchAlbumList(id: String?) {
        showLoadingProgressLiveData.postValue(true)
        userInteractor.fetchAlbum(id, object : InteractorCallBack<List<Album>?> {
            override fun onSuccess(response: List<Album>?) {
                showLoadingProgressLiveData.postValue(false)
                albumListLiveData.postValue(response)
            }

            override fun onFailure(throwable: Throwable) {
                showLoadingProgressLiveData.postValue(false)
                errorProgressLiveData.postValue(null)
            }

        })
    }
}
