package com.artique.folders

import com.artique.models.AlbumContent
import com.artique.models.MimeType
import com.artique.repository.DefaultGalleryRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class FetchFoldersUseCase @Inject constructor(
    private val galleryRepository: DefaultGalleryRepository,
) {
    private val albumContents: MutableList<AlbumContent> = arrayListOf()

    suspend fun fetch(): List<AlbumContent> = withContext(Dispatchers.Default) {
        val mediaContent = galleryRepository.fetchExternalStorageMediaContents()


        /*Adding All Image item for list*/
        mediaContent.find {
            it.contentType.contains(PICTURE)
        } ?.let {
            val totalPictures = mediaContent.filter { it.contentType.contains(PICTURE)}.size

            albumContents.add(
                AlbumContent(
                    displayPicture = it.path,
                    displayName = "All Images",
                    mimeType = MimeType.Picture,
                    count = totalPictures.toString()
                )
            )
        }

        /*Adding All Video item for list*/
        mediaContent.find {
            it.contentType.contains(MOVIE)
        }?.let {
            val totalMovies = mediaContent.filter { it.contentType.contains(MOVIE)}.size

            albumContents.add(
                AlbumContent(
                    displayPicture = it.path,
                    displayName = "All Videos",
                    mimeType = MimeType.Movies,
                    count = totalMovies.toString()
                )
            )
        }

        /*Creating All items for list*/
        mediaContent.distinctBy {
            it.folderName
        }.map {
            val mimeType = when (it.folderName.lowercase()) {
                "all images" -> MimeType.Picture
                "all videos" -> MimeType.Movies
                else -> MimeType.Any
            }

            val totalMedia = mediaContent.filter { item -> it.folderName == item.folderName }.size

            AlbumContent(
                displayPicture = it.path,
                displayName = it.folderName,
                mimeType = mimeType,
                count = totalMedia.toString()
            )
        }.also {
            albumContents.addAll(it)
        }

        albumContents
    }


    companion object {
        private const val MOVIE = "video"
        private const val PICTURE = "image"
    }
}