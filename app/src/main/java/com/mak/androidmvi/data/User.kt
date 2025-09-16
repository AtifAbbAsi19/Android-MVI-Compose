package com.mak.androidmvi.data

import androidx.compose.runtime.Immutable


@Immutable
data class User(val id: String, val name: String, val isActive : Boolean)