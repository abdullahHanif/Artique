package com.artique.folderdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artique.models.AlbumContent
import com.artique.models.MimeType.Any
import com.artique.models.MimeType.Movies
import com.artique.models.MimeType.Picture
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FolderDetailsViewModel @Inject constructor(private val fetchFolderDetailsUseCase: FetchFolderDetailsUseCase) :
    ViewModel() {

    private val _folders: MutableStateFlow<FoldersDetailsState> =
        MutableStateFlow(FoldersDetailsState())
    val folders: StateFlow<FoldersDetailsState> = _folders.asStateFlow()

    fun handleArguments(albumContent: AlbumContent) {
        val filters = when (albumContent.mimeType) {
            Picture, Movies -> FolderDetailsFetchFilter.ByMimeType(albumContent.mimeType)
            Any -> FolderDetailsFetchFilter.ByFolderName(albumContent.displayName)
        }

        loadFolders(filters)
    }

    private fun loadFolders(folderDetailsFetchFilter: FolderDetailsFetchFilter) {
        viewModelScope.launch() {
            with(fetchFolderDetailsUseCase.fetch(folderDetailsFetchFilter)) {
                _folders.update { state ->
                    state.copy(listing = this)
                }
            }
        }
    }
}

data class FoldersDetailsState(
    var listing: List<AlbumContent> = listOf(),
)