package com.artique.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumContent(
    var displayPicture: String = "",
    var displayName: String = "",
    val count: String = "",
    val mimeType: MimeType = MimeType.Any,
) : Parcelable

