package com.artique.common

import android.Manifest.permission
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build.VERSION_CODES
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.artique.common.PermissionHandler.PermissionResult
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface PermissionHandler {
    fun requestPermissions(
        permissionTypes: Array<PermissionType>,
        lifecycleOwner: LifecycleOwner,
        activityResultRegistry: ActivityResultRegistry,
        callback: PermissionResult,
    )

    fun hasPermissions(permissions: Array<PermissionType>): Boolean

    interface PermissionResult {
        fun onGranted(grantedPermissions: Array<PermissionType>)
        fun onDenied(deniedPermissions: Array<PermissionType>)
        fun onDeniedAll(deniedPermissions: Array<PermissionType>)
    }
}

@Singleton
class DefaultPermissionHandler @Inject constructor(@ApplicationContext val context: Context) :
    PermissionHandler {

    private fun hasPermission(permissionType: PermissionType) =
        ContextCompat.checkSelfPermission(
            context,
            permissionType.permissionName,
        ) == PackageManager.PERMISSION_GRANTED

    override fun requestPermissions(
        permissionTypes: Array<PermissionType>,
        lifecycleOwner: LifecycleOwner,
        activityResultRegistry: ActivityResultRegistry,
        callback: PermissionResult,
    ) {
        val permissionLauncher: ActivityResultLauncher<Array<String>> =
            activityResultRegistry.register(
                PERMISSION_REGISTRY_KEY,
                lifecycleOwner,
                ActivityResultContracts.RequestMultiplePermissions(),
            ) { isPermissionGrantedMap ->

                val grantedPermissionSize = isPermissionGrantedMap.filter { it.value }.size

                when {
                    grantedPermissionSize == isPermissionGrantedMap.size -> {
                        callback.onGranted(
                            convertToPermissionTypeFromPermissionName(
                                isPermissionGrantedMap.keys
                            )
                        )
                    }

                    (grantedPermissionSize > 0 && grantedPermissionSize < isPermissionGrantedMap.size) -> {
                        val notGranted = isPermissionGrantedMap.filter { it.value.not() }.keys
                        callback.onDenied(convertToPermissionTypeFromPermissionName(notGranted))
                    }

                    (grantedPermissionSize == 0) -> {
                        callback.onDeniedAll(
                            convertToPermissionTypeFromPermissionName(
                                isPermissionGrantedMap.keys
                            )
                        )
                    }
                }
            }

        permissionLauncher.launch(permissionTypes.toNameArray())
    }

    private fun convertToPermissionTypeFromPermissionName(strings: Set<String>): Array<PermissionType> {
        return strings.mapNotNull { str ->
            PermissionType.values().find { it.permissionName == str }
        }.toTypedArray()
    }

    override fun hasPermissions(permissions: Array<PermissionType>): Boolean {
        return permissions.filter {
            hasPermission(it)
        }.size == permissions.size
    }

    companion object {
        const val PERMISSION_REGISTRY_KEY = "PermissionHandlerRegistryKey"
    }
}

enum class PermissionType(val permissionName: String) {
    ReadAllFiles(permission.READ_EXTERNAL_STORAGE),
    @RequiresApi(VERSION_CODES.TIRAMISU)
    GalleryImages(permission.READ_MEDIA_IMAGES),
    @RequiresApi(VERSION_CODES.TIRAMISU)
    GalleryVideos(permission.READ_MEDIA_VIDEO)
}

fun Array<PermissionType>.toNameArray(): Array<String> {
    return this.map {
        it.permissionName
    }.toTypedArray()
}

