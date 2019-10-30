package com.test.app.interactor

import com.test.app.network.UserAlbumApi
import com.test.app.model.Album
import com.test.app.model.InteractorCallBack
import com.test.app.model.User
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Call

class UserInteractorTest {

    lateinit var userAlbumApi: UserAlbumApi

    lateinit var userInteractor: UserInteractor

    @Mock
    lateinit var call: Call<List<User>?>

    @Mock
    lateinit var call2: Call<List<Album>?>

    @Mock
    lateinit var interactorCallBack: InteractorCallBack<List<User>?>

    @Mock
    lateinit var interactorCallBack2: InteractorCallBack<List<Album>?>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userAlbumApi = Mockito.mock(UserAlbumApi::class.java)
        userInteractor =
            UserInteractor(userAlbumApi)
    }

    @Test
    fun `WHEN interactor fetch users called THEN should trigger api call for fetch users`() {
        Mockito.`when`(userAlbumApi.fetchUsers())
            .thenReturn(call)

        userInteractor.fetchUsers(interactorCallBack)
        Mockito.verify(userAlbumApi).fetchUsers()
    }

    @Test
    fun `WHEN interactor fetch album called THEN should trigger api call for fetch albums`() {
        Mockito.`when`(userAlbumApi.fetchAlbumForId("id"))
            .thenReturn(call2)

        userInteractor.fetchAlbum("id", interactorCallBack2)
        Mockito.verify(userAlbumApi).fetchAlbumForId("id")
    }
}
