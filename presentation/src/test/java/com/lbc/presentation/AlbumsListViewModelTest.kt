package com.lbc.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.lbc.domain.use_case.GetAlbumsImpl
import com.lbc.presentation.utils.getOrAwaitValue
import com.lbc.presentation.viewmodel.AlbumsListViewModel
import junit.framework.TestCase
import kotlinx.coroutines.delay

import org.junit.*
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AlbumsListViewModelTest: TestCase() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val fakeAlbumGeneratorRepository = FakeAlbumGeneratorRepository(ALBUMS_LIST_SIZE)
    private val getAlbumsUseCase = GetAlbumsImpl(fakeAlbumGeneratorRepository)
    private val viewModel = AlbumsListViewModel(getAlbumsUseCase, SavedStateHandle())

    @Test
    fun `when fetching albums, loading updated`() = runBlocking {
        viewModel.getAlbums()
        assertEquals(viewModel.loadingState.getOrAwaitValue(), true)
        delay(1000)
        assertEquals(viewModel.loadingState.getOrAwaitValue(), false)
    }

    @Test
    fun `When success or error with no empty list, loading initial list of albums`() = runBlocking {
        viewModel.getAlbums()
        val expected = fakeAlbumGeneratorRepository.getFakeAlbums().subList(0, 20)
        val result = viewModel.albumsListState.getOrAwaitValue().albumsList
        assertEquals(expected, result)
    }


    @Test
    fun `when pagination needed return next page`() = runBlocking {
        viewModel.getAlbums()
        val expected = fakeAlbumGeneratorRepository.getFakeAlbums().subList(0, 20)
        val result = viewModel.albumsListState.getOrAwaitValue().albumsList
        assertEquals(expected, result)
        viewModel.loadMoreAlbums()
        val expectedMoreAlbums = fakeAlbumGeneratorRepository.getFakeAlbums().subList(0, 40)
        val resultMoreAlbums = viewModel.albumsListState.getOrAwaitValue().albumsList
        assertEquals(expectedMoreAlbums, resultMoreAlbums)
    }

    companion object {
        const val ALBUMS_LIST_SIZE = 5000
    }
}


