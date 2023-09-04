package com.example.rdpasswordquality.extensions

import android.annotation.TargetApi
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast

fun Context.setSelectedPackage(pkg: String?, componentName: ComponentName, devicePolicyManager: DevicePolicyManager) {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setAlwaysOnVpnPackageQPlus(pkg, devicePolicyManager, componentName)
        } else {
            devicePolicyManager.setAlwaysOnVpnPackage(componentName, pkg,  /* lockdownEnabled */true)
        }
    } catch (e: PackageManager.NameNotFoundException) {
        val text = "Package not found: " + e.message
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    } catch (e: UnsupportedOperationException) {
        Toast.makeText(this, "App doesn't support always-on VPN", Toast.LENGTH_SHORT)
            .show()
    }
}

@TargetApi(Build.VERSION_CODES.Q)
@Throws(PackageManager.NameNotFoundException::class)
fun setAlwaysOnVpnPackageQPlus(
    pkg: String?,
    devicePolicyManager: DevicePolicyManager,
    componentName: ComponentName
) {
    val lockdown: Boolean = false // for now
    devicePolicyManager.setAlwaysOnVpnPackage(componentName, pkg, lockdown)
}