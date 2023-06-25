package com.artique.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.artique.HomeActivity
import com.artique.common.DefaultPermissionHandler
import com.artique.common.PermissionHandler.PermissionResult
import com.artique.common.PermissionType
import com.artique.common.PermissionType.GalleryVideos
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var permissionHandler: DefaultPermissionHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(PermissionType.GalleryImages, GalleryVideos)
        } else {
            arrayOf(PermissionType.ReadAllFiles)
        }

        permissionHandler.requestPermissions(permissions, this, this.activityResultRegistry,
            object : PermissionResult {
                override fun onGranted(grantedPermissions: Array<PermissionType>) {
                    startActivity(Intent(this@SplashScreenActivity, HomeActivity::class.java))
                    finish()
                }

                override fun onDenied(deniedPermissions: Array<PermissionType>) {
                    Toast.makeText(this@SplashScreenActivity, "Permission Denied", Toast.LENGTH_LONG).show()
                    finish()
                }

                override fun onDeniedAll(deniedPermissions: Array<PermissionType>) {
                    Toast.makeText(this@SplashScreenActivity, "All Permission Denied", Toast.LENGTH_LONG).show()
                    finish()
                }
            })


    }
}