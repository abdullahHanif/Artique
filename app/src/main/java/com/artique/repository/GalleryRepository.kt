package com.artique.repository

import com.artique.common.ContentHandler
import com.artique.common.DefaultContentHandler
import com.artique.models.FolderContent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject


interface GalleryRepository {
    fun fetchExternalStorageMediaContents(): List<FolderContent>
}

@ViewModelScoped
class DefaultGalleryRepository @Inject constructor(private val contentHandler: DefaultContentHandler) :
    GalleryRepository {
    override fun fetchExternalStorageMediaContents(): List<FolderContent> =
        contentHandler.queryContentFromDevice()

}