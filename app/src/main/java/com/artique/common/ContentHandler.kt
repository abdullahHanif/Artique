package com.artique.common

import android.content.ContentResolver
import android.content.ContentUris
import android.provider.MediaStore
import com.artique.models.FolderContent
import javax.inject.Inject
import javax.inject.Singleton

interface ContentHandler {
    fun queryContentFromDevice(): List<FolderContent>
}

@Singleton
class DefaultContentHandler @Inject constructor(val contentResolver: ContentResolver) :
    ContentHandler {

    override fun queryContentFromDevice(): List<FolderContent> {
        val folderContentList = mutableListOf<FolderContent>()

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.DATE_ADDED
        )
        val query = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon().build()
        val sortOrder = "${MediaStore.Images.Media._ID} DESC"

        contentResolver.query(query, projection, null, null, sortOrder)?.use { cursor ->
            try {
                with(cursor) {
                    while (moveToNext()) {
                        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

                        val displayName =
                            getString(getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                        val folderName =
                            getString(getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
                        val path =
                            getString(getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

                        val id = cursor.getLong(idColumn)

                        val contentUri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            id
                        )
                        val contentType =
                            getString(getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE))
                        val createdAt =
                            getLong(getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED))

                        val folderContentItem =
                            FolderContent(
                                path = path,
                                displayName = displayName,
                                folderName = folderName,
                                uri = contentUri.toString(),
                                contentType = contentType,
                                createdAt = createdAt
                            )
                        folderContentList.add(folderContentItem)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor.close()
            }
        }

        return folderContentList
    }
}