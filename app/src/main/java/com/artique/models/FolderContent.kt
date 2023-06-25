package com.artique.models

data class FolderContent(
    var uri: String? = "",
    var path: String = "",
    var displayName: String = "",
    val folderName: String = "",
    val contentType: String,
    val createdAt: Long,
)