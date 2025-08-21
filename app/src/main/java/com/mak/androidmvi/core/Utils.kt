package com.mak.androidmvi.core

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder

/*
@Composable
fun String.asNetworkPainter(
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
): Painter {
    return rememberAsyncImagePainter(
        model = this,
        placeholder = placeholder?.let { painterResource(it) },
        error = error?.let { painterResource(it) }
    )
}*/

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController) : T{

    val navGraphRoute = destination.parent?.route?:return viewModel()
    val parentEntry = remember (this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel (parentEntry)
}



@Composable
fun Int.asPainter(): Painter = painterResource(id = this)

inline fun <reified T> NavController.navigateTo(obj: T) {
    val json = URLEncoder.encode(Json.encodeToString(obj), "UTF-8")
    this.navigate("${obj!!::class.simpleName}/$json")
}

inline fun <reified T> NavBackStackEntry.getArgs(): T? {
    val json = arguments?.getString("args") ?: return null
    return Json.decodeFromString<T>(URLDecoder.decode(json, "UTF-8"))
}