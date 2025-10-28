package com.mak.androidmvi.ui.screens.splash

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mak.androidmvi.core.asPainter
import com.mak.androidmvi.ui.core.RootViewModel
import com.mak.androidmvi.ui.viewmodel.AppSharedViewModel
import kotlinx.coroutines.delay


val curvedShape = GenericShape { size, _ ->
    val width = size.width
    val height = size.height

    val radius = width * 0.8f // adjust curvature (0.5f–1f looks good)

    moveTo(0f, 0f) // top-left corner
    lineTo(width, 0f) // top-right
    lineTo(width, height - radius) // straight down a bit

    // Draw bottom-right curve (a quarter circle)
    quadraticBezierTo(
        width, height,      // control point
        width - radius, height  // end point
    )

    // bottom-left corner curve
    quadraticBezierTo(
        0f, height,         // control point
        0f, height - radius // end point
    )

    close()
}


@Composable
fun SplashScreen(
    onFinished: () -> Unit,
    appSharedViewModel: AppSharedViewModel,
    rootViewModel: RootViewModel
) {

    val viewModel: SplashViewModel = viewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    val onFinishedState by rememberUpdatedState(onFinished)

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SplashViewModel.SplashEffect.NavigateToAuth -> onFinishedState()
            }
        }
    }


    // Start a coroutine when this composable enters the composition
    LaunchedEffect(Unit) {
        delay(3000) // 3 seconds
        onFinishedState() // Navigate to next screen
    }

    // Your UI for Splash
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        Image(
            modifier = Modifier.clip(curvedShape),
            painter = uiState.logo.asPainter(),
            contentDescription = "splash_logo"
        )

      // if (uiState.isLoading) {
        //    CircularProgressIndicator()
        //}

        Text(
            text = uiState.version,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 24.dp)
        )

    }

}




val upsideDownBlobShape = GenericShape { size, _ ->
    val width = size.width
    val height = size.height

    val controlX1 = width * 0.1f
    val controlY1 = height * 0.2f
    val controlX2 = width * 0.8f
    val controlY2 = -height * 0.3f

    moveTo(0f, 0f)
    cubicTo(
        controlX1, controlY1,
        controlX2, controlY2,
        width, 0f
    )
    lineTo(width, height)
    lineTo(0f, height)
    close()
}


val leftCurveShape = GenericShape { size, _ ->
    val width = size.width
    val height = size.height

    val curveDepth = width * 0.25f // how deep the curve goes inward
    val topCurveHeight = height * 0.1f // small top curve

    val path = Path().apply {
        // Start slightly below the top-left for smooth entry
        moveTo(0f, topCurveHeight)

        // Create a smooth curve from top-left to bottom-left
        quadraticTo(
            -curveDepth, height * 0.5f, // control point inward
            0f, height                  // end at bottom-left
        )

      /*  // Replace the right-side curve with:
        cubicTo(
            width, height * 0.3f,  // first control point
            width - rightCurveDepth, height * 0.7f,  // second control point
            width, height           // end point
        )*/

        // Bottom edge
        lineTo(width, height)

        // Right edge
        lineTo(width, 0f)

        // Top edge
        lineTo(0f, 0f)

        close()
    }

    addPath(path)
}


fun CurvedEdgeShape(
    topCurve: Float = 30f,       // smaller top curve
    bottomStartCurve: Float = 100f // bigger bottom-start curve
): GenericShape = GenericShape { size, _ ->
    val width = size.width
    val height = size.height

    val path = Path().apply {
        // Start from top-left corner
        moveTo(0f, topCurve)

        // Top edge curve (gentle curve)
        quadraticTo(
            width / 2, 0f,
            width, topCurve
        )

        // Right edge
        lineTo(width, height)

        // Bottom edge - curve towards bottom start (left)
        quadraticTo(
            width / 4, height - bottomStartCurve,
            0f, height - bottomStartCurve / 2
        )

        close()
    }
    addPath(path)
}