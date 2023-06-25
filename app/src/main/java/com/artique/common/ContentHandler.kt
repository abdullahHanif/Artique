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
        val imageQuery = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon().build()
        val imageSortOrder = "${MediaStore.Images.Media._ID} DESC"

        val videoQuery = MediaStore.Video.Media.EXTERNAL_CONTENT_URI.buildUpon().build()
        val videoSortOrder = "${MediaStore.Video.Media._ID} DESC"

        contentResolver.query(imageQuery, projection, null, null, imageSortOrder)?.use{ cursor ->
            try {
                while (cursor.moveToNext()) {
                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

                    val displayName =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                    val folderName =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
                    val path =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

                    val id = cursor.getLong(idColumn)

                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    val contentType =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE))
                    val createdAt =
                        cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED))

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
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor.close()
            }
        }

        contentResolver.query(videoQuery, projection, null, null, videoSortOrder)?.use { cursor ->
            try {
                while (cursor.moveToNext()) {
                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)

                    val displayName =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
                    val folderName =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME))
                    val path =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))

                    val id = cursor.getLong(idColumn)

                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    val contentType =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE))
                    val createdAt =
                        cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED))

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
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor.close()
            }
        }

        return folderContentList
    }
}