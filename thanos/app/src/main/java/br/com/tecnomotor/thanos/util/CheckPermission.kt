package br.com.tecnomotor.thanos.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat

class CheckPermission {
    companion object {
        val PERMISSIONS = arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_ADMIN
        )


        fun permissionsGranted(context: Context, permissions: Array<String>): Boolean {
            var isGranted = true
            permissions.forEach { permission ->
                when(Build.VERSION.SDK_INT) {
                    Build.VERSION_CODES.S ->  //SDK = 31
                        if (permission in arrayOf(Manifest.permission.BLUETOOTH_ADMIN)) return@forEach
                    Build.VERSION_CODES.R, Build.VERSION_CODES.Q ->  //SDK = 30 and SDK = 29
                        if (permission in arrayOf(Manifest.permission.BLUETOOTH_CONNECT,
                                Manifest.permission.BLUETOOTH_SCAN)) return@forEach
                    else ->
                        if (permission in arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                                Manifest.permission.BLUETOOTH_CONNECT,
                                Manifest.permission.BLUETOOTH_SCAN)) return@forEach
                }
                isGranted = (isGranted && ActivityCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_GRANTED)
                Log.w(this.javaClass.simpleName, "Permission $permission: $isGranted")
            }
            return isGranted
        }
    }
}