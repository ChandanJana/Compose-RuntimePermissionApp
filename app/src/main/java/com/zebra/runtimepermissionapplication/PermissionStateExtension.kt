package com.zebra.runtimepermissionapplication

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

/**
 * Created by Chandan Jana on 14-08-2023.
 * Company name: Mindteck
 * Email: chandan.jana@mindteck.com
 */

@ExperimentalPermissionsApi
fun PermissionState.isPermanentlyDenied(): Boolean {
    return !shouldShowRationale && !hasPermission
}