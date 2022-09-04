package com.lbc.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lbc.app.DefaultDispatcher
import com.lbc.domain.use_case.GetAlbums
import com.lbc.domain.utils.Resource
import com.lbc.presentation.model.AlbumUi
import com.lbc.presentation.model.toAlbumUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsListViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbums,
    private val savedStateHandle: SavedStateHandle,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    companion object {
        const val PAGE_ALBUMS_COUNT = 20
        const val LAST_INDEX_KEY = "last_index_key"
    }
    private var fromIndex = 0

    private val currentAlbums = mutableListOf<AlbumUi>()
    private val albumsList = mutableListOf<AlbumUi>()

    private val _albumsListState = MutableLiveData<AlbumUiState>()
    val albumsListState = _albumsListState

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState = _loadingState

    fun getAlbums(refresh: Boolean = false) {
        viewModelScope.launch(dispatcher) {
            if (albumsList.isEmpty() || refresh) {
                _loadingState.postValue(true)
                when (val result = getAlbumsUseCase()) {
                    is Resource.Error -> {
                        pagingAlbums(
                            albums = result.data?.map { it.toAlbumUi() } ?: emptyList(),
                            errorMessage = result.errorMessage)
                    }
                    is Resource.Success -> {
                        pagingAlbums(
                            albums = result.data.map { it.toAlbumUi() },
                            errorMessage = null
                        )
                    }
                }
            } else {
                _albumsListState.postValue(
                    AlbumUiState(
                        _albumsListState.value?.errorMessage,
                        currentAlbums
                    )
                )
            }
        }
    }

    private fun pagingAlbums(albums: List<AlbumUi>, errorMessage: String?) {
        if (albums.isNotEmpty()) {
            albumsList.addAll(albums)
            val lastIndex = savedStateHandle.get<Int>(LAST_INDEX_KEY)
            val toIndex = lastIndex ?: PAGE_ALBUMS_COUNT
            currentAlbums.addAll(albumsList.subList(fromIndex, toIndex))
            fromIndex = toIndex
            saveLastIndex(fromIndex)
        }
        _albumsListState.postValue(
            AlbumUiState(
                errorMessage,
                currentAlbums
            )
        )
        _loadingState.postValue(false)
    }

    fun loadMoreAlbums() {
        if (fromIndex < albumsList.size) {
            val newIndex = fromIndex + PAGE_ALBUMS_COUNT
            currentAlbums.addAll(albumsList.subList(fromIndex, newIndex))
            fromIndex = newIndex
            saveLastIndex(fromIndex)
            albumsListState.postValue(
                AlbumUiState(
                    _albumsListState.value?.errorMessage,
                    currentAlbums
                )
            )
        }
    }

    private fun saveLastIndex(index: Int) {
        savedStateHandle[LAST_INDEX_KEY] = index
    }
}