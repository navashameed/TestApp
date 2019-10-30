package com.test.app.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.app.any
import com.test.app.capture
import com.test.app.model.Album
import com.test.app.model.InteractorCallBack
import com.test.app.interactor.UserInteractor
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*


class UserViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    lateinit var userViewModel: UserViewModel

    @Mock
    lateinit var userInteractor: UserInteractor

    @Captor
    lateinit var argCaptor: ArgumentCaptor<InteractorCallBack<List<Album>?>>

    @Captor
    lateinit var argCaptorId: ArgumentCaptor<String>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userViewModel = UserViewModel(userInteractor)
    }

    @Test
    fun `WHEN ViewModel users list fetched THEN progress dialog is shown and interactor fetchUsers is invoked`() {
        userViewModel.fetchUserList()
        Assert.assertEquals(userViewModel.loadingProgressDialogObservable.value, true)
        Mockito.verify(userInteractor).fetchUsers(any())
    }

    @Test
    fun `WHEN ViewModel users album fetched THEN progress dialog is shown and interactor fetchUserAlbum is invoked`() {
        userViewModel.fetchAlbumList("id")
        Assert.assertEquals(userViewModel.loadingProgressDialogObservable.value, true)
        Mockito.verify(userInteractor).fetchAlbum(capture(argCaptorId), capture(argCaptor))

        assertEquals(argCaptorId.value, "id")
        assertTrue(argCaptor.value is InteractorCallBack);
    }
}
