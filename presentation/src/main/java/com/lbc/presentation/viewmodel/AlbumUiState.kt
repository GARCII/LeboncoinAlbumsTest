package com.lbc.presentation.viewmodel

import com.lbc.presentation.model.AlbumUi
import java.io.Serializable

data class AlbumUiState(val errorMessage: String?, val albumsList: List<AlbumUi>) : Serializable