package com.artique

import com.artique.models.AlbumContent
import com.artique.models.MimeType.Any

class DummyDataRepo {
    companion object {
        fun provideFoldersList(): List<AlbumContent> {
            return mutableListOf(
                AlbumContent(
                    "SOME_DATA",
                    "SOME_DATA",
                    "1",
                    Any
                ),
                AlbumContent(
                    "SOME_DATA",
                    "SOME_DATA",
                    "1",
                    Any
                ),
                AlbumContent(
                    "SOME_DATA",
                    "SOME_DATA",
                    "1",
                    Any
                ),
                )
        }
    }
}