package com.example.homework4

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PermissionRequestHandler(
    activity: AppCompatActivity,
    private val callback: (() -> Unit)? = null,
    private val deniedCallback: (() -> Unit)? = null,
) {

    private var currentPermission = ""

    private val singlePermissionLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                if (!activity.shouldShowRequestPermissionRationale(currentPermission)) {
                    deniedCallback?.invoke()
                }
            } else {
                callback?.invoke()
            }
        }


    fun requestPermission(permission: String) {
        this.currentPermission = permission
        singlePermissionLauncher.launch(permission)
    }
}