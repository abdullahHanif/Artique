package com.artique.folderdetail

import com.artique.folderdetail.FolderDetailsFetchFilter.ByFolderName
import com.artique.folderdetail.FolderDetailsFetchFilter.ByMimeType
import com.artique.models.AlbumContent
import com.artique.models.MimeType
import com.artique.models.MimeType.Movies
import com.artique.models.MimeType.Picture
import com.artique.repository.DefaultGalleryRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class FetchFolderDetailsUseCase @Inject constructor(private val galleryRepository: DefaultGalleryRepository) {

    suspend fun fetch(filters: FolderDetailsFetchFilter): List<AlbumContent> =
        withContext(Dispatchers.IO) {
            galleryRepository.fetchExternalStorageMediaContents().filter {
                when (filters) {
                    is ByFolderName -> it.folderName == filters.folderName

                    is ByMimeType -> {
                        if (filters.mimeType == Movies) it.contentType.contains(MOVIE)
                        else if (filters.mimeType == Picture) it.contentType.contains(PICTURE)
                        else true
                    }
                }
            }.map {
                AlbumContent(
                    displayName = it.displayName,
                    displayPicture = it.path,
                    count = "1",
                    mimeType = if (it.contentType.contains(MOVIE)) Movies else Picture
                )
            }
        }

    companion object {
        private const val MOVIE = "video"
        private const val PICTURE = "image"
    }

}

sealed class FolderDetailsFetchFilter() {
    class ByFolderName(val folderName: String) : FolderDetailsFetchFilter()
    class ByMimeType(val mimeType: MimeType) : FolderDetailsFetchFilter()
}