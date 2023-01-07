package com.bridge.softwares.vesos

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionsUtils {
    private const val REQUEST_PERMISSION_MULTIPLE = 100
    private const val REQUEST_WRITE_EXTERNAL = 101

    fun checkAndRequestPermissions(activity: Activity): Boolean {
        val permissionWriteExternal = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        // Permission List
        val listPermissionsNeeded: MutableList<String> = ArrayList()

        // Read/Write Permission
        if (permissionWriteExternal != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                activity,
                listPermissionsNeeded.toTypedArray(),
                REQUEST_PERMISSION_MULTIPLE
            )
            return false
        }
        return true
    }

    fun requestWriteExternalPermission(activity: Activity?) {
        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                Toast.makeText(
                    activity,
                    "Write permission is needed to create Excel file ",
                    Toast.LENGTH_SHORT
                ).show()
                // Show an explanation to the user *asynchronously* -- don't
                // block this thread waiting for the user's response! After the
                // user sees the explanation, try again to request the
                // permission.
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_WRITE_EXTERNAL
                )
                Toast.makeText(activity, "REQUEST Write PERMISSION", Toast.LENGTH_LONG).show()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_WRITE_EXTERNAL
                )
            }
        }
    }

    fun hasPermissions(
        context: Context?,
        vararg permissions: String
    ): Boolean {
        if (context != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }
}