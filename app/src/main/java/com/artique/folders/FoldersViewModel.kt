package com.artique.folders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artique.models.AlbumContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FoldersViewModel @Inject constructor(private val fetchFoldersUseCase :FetchFoldersUseCase) : ViewModel() {

    private val _folders: MutableStateFlow<FoldersState> = MutableStateFlow(FoldersState())
    val folders: StateFlow<FoldersState> = _folders.asStateFlow()

    init {
        loadFolders()
    }

     fun loadFolders() {
        viewModelScope.launch() {
            with(fetchFoldersUseCase.fetch()) {
                _folders.update { state ->
                    state.copy(listing = this)
                }
            }
        }
    }
}

data class FoldersState(
    var listing: List<AlbumContent> = listOf(),
)