package com.mak.androidmvi.ui.extensions

import android.text.TextUtils

fun String.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String?.safeEmpty(): String {
    return ""
}


fun String.isValidPassword(
    minLength: Int = 8,
    requireUppercase: Boolean = false,
    requireLowercase: Boolean = false,
    requireDigit: Boolean = false,
    requireSpecialChar: Boolean = false
): Boolean {

    if (length < minLength) return false

    if (requireUppercase && !any { it.isUpperCase() }) return false
    if (requireLowercase && !any { it.isLowerCase() }) return false
    if (requireDigit && !any { it.isDigit() }) return false
    if (requireSpecialChar && !any { !it.isLetterOrDigit() }) return false

    return true
}