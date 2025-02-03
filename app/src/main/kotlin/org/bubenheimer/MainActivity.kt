package org.bubenheimer

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startLocationService()
    }

    private fun startLocationService() {
        if (checkSelfPermission(ACCESS_FINE_LOCATION) == PERMISSION_GRANTED ||
            checkSelfPermission(ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED) {
            reallyStartLocationService()
        } else {
            requestPermissions()
        }
    }

    private fun reallyStartLocationService() {
        startService(Intent(this, LocationService::class.java))
    }

    private fun requestPermissions() {
        requestPermissions(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION), PRC)
    }

    @Deprecated("")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PRC) {
            if (grantResults.contains(PERMISSION_GRANTED)) {
                reallyStartLocationService()
            } else {
                requestPermissions()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
    private companion object {
        const val PRC: Int = 1234
    }
}
