package com.mak.androidmvi.ui.screens.success

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A polished, reusable success page for Compose (Material3).
 *
 * Features
 * - Animated checkmark inside a success circle (no external libs)
 * - Optional confetti burst
 * - Title, message, primary & secondary actions
 * - Works with edge-to-edge & system bars
 * - Accessible (semantics, test tags)
 */
@Composable
fun SuccessScreen(
    modifier: Modifier = Modifier,
    title: String = "Success!",
    message: String = "Your action completed successfully.",
    primaryActionLabel: String = "Continue",
    onPrimaryAction: () -> Unit = {},
    secondaryActionLabel: String? = null,
    onSecondaryAction: (() -> Unit)? = null,
    showConfetti: Boolean = true,
    circleSize: Dp = 120.dp,
    circleColor: Color = MaterialTheme.colorScheme.primary,
    checkColor: Color = MaterialTheme.colorScheme.onPrimary,
    contentPadding: PaddingValues = PaddingValues(24.dp),
    // Optional slots to override the illustration if you have a design-system icon
    illustration: (@Composable () -> Unit)? = null,
    enter: EnterTransition = fadeIn(animationSpec = tween(400)),
    exit: ExitTransition = fadeOut(animationSpec = tween(300))
) {
    val successDesc = "Success state"

    Surface(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .semantics { contentDescription = successDesc },
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .testTag("success-content"),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                AnimatedVisibility(visible = true, enter = enter, exit = exit) {
                    if (illustration != null) illustration() else AnimatedSuccessMark(
                        size = circleSize,
                        circleColor = circleColor,
                        checkColor = checkColor
                    )
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )

                Spacer(Modifier.height(8.dp))

                // Actions
                Column(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onPrimaryAction,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("success-primary"),
                    ) { Text(primaryActionLabel) }

                    if (secondaryActionLabel != null && onSecondaryAction != null) {
                        OutlinedButton(
                            onClick = onSecondaryAction,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("success-secondary")
                        ) { Text(secondaryActionLabel) }
                    }
                }
            }

            if (showConfetti) ConfettiBurst(modifier = Modifier.fillMaxSize())
        }
    }
}

/**
 * Draws an animated circular success badge with a checkmark.
 */
@Composable
private fun AnimatedSuccessMark(
    size: Dp,
    circleColor: Color,
    checkColor: Color
) {
    val duration = 900
    var start by remember { mutableStateOf(false) }

    val circleProgress by animateFloatAsState(
        targetValue = if (start) 1f else 0f,
        animationSpec = tween(durationMillis = duration, easing = FastOutSlowInEasing),
        label = "circleProgress"
    )
    val checkProgress by animateFloatAsState(
        targetValue = if (start) 1f else 0f,
        animationSpec = tween(durationMillis = duration, delayMillis = 250, easing = FastOutSlowInEasing),
        label = "checkProgress"
    )

    // Start animation when composed
    LaunchedEffect(Unit) { start = true }

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(circleColor.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size * 0.82f)) {
            val stroke = size.toPx() * 0.08f
            // Circle stroke (animated sweep)
            drawArc(
                color = circleColor,
                startAngle = -90f,
                sweepAngle = 360f * circleProgress,
                useCenter = false,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )

            // Checkmark path proportions
            val w = size.toPx() * 0.82f
            val h = w
            val left = (size.toPx() - w) / 2
            val top = (size.toPx() - h) / 2

            val p = Path().apply {
                // A nice check shape: start ~40%, down to 52%, then up to 78%
                moveTo(left + w * 0.28f, top + h * 0.52f)
                lineTo(left + w * 0.45f, top + h * 0.68f)
                lineTo(left + w * 0.76f, top + h * 0.36f)
            }

            // Draw partial path based on progress
            val measure = androidx.compose.ui.graphics.PathMeasure()
            measure.setPath(p, false)
            val length = measure.length
            val partial = Path().apply {
                measure.getSegment(0f, length * checkProgress, this)
            }
            drawPath(
                path = partial,
                color = checkColor,
                style = Stroke(width = stroke, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }
    }
}

/**
 * Lightweight confetti burst using an infinite transition. Disable with [showConfetti=false].
 */
@Composable
private fun ConfettiBurst(modifier: Modifier = Modifier) {
    // Avoid heavy animations in Preview to keep it snappy
    val isPreview = LocalInspectionMode.current
    val particleCount = if (isPreview) 0 else 18

    val infinite = rememberInfiniteTransition(label = "confetti")
    val offsets = List(particleCount) { i ->
        val x by infinite.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1500 + (i * 40), easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "x$i"
        )
        val y by infinite.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000 + (i * 55), easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "y$i"
        )
        x to y
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val center = Offset(w / 2f, h / 2.2f)
        val radius = minOf(w, h) * 0.35f
        val stroke = radius * 0.02f

        offsets.forEachIndexed { i, (ux, uy) ->
            // Emit from center at different angles
            val angle = (i * (360f / (offsets.size.coerceAtLeast(1)))) * (Math.PI / 180f)
            val dir = Offset(
                x = (kotlin.math.cos(angle).toFloat()),
                y = (kotlin.math.sin(angle).toFloat())
            )
            val start = center + dir * (radius * 0.15f)
            val end = center + dir * (radius * (0.6f + ux * 0.4f))
            drawLine(
                color = Color.Gray.copy(alpha = 0.35f + (uy * 0.25f)),
                start = start,
                end = end,
                strokeWidth = stroke
            )
        }
    }
}

// ---------- Previews ----------

@Preview(showBackground = true)
@Composable
private fun SuccessScreenPreviewLight() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        SuccessScreen(
            title = "Payment received",
            message = "We emailed your receipt. You can now explore premium features.",
            primaryActionLabel = "Go to Dashboard",
            onPrimaryAction = {},
            secondaryActionLabel = "View receipt",
            onSecondaryAction = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SuccessScreenPreviewDark() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        SuccessScreen(
            title = "Profile updated",
            message = "Your changes have been saved.",
            primaryActionLabel = "Done",
            onPrimaryAction = {},
            secondaryActionLabel = "Edit again",
            onSecondaryAction = {},
            showConfetti = false
        )
    }
}

// ---------- Optional: Dialog version ----------

@Composable
fun SuccessDialog(
    onDismiss: () -> Unit,
    title: String,
    message: String,
    primaryActionLabel: String,
    onPrimaryAction: () -> Unit,
    secondaryActionLabel: String? = null,
    onSecondaryAction: (() -> Unit)? = null,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            AnimatedSuccessMark(
                size = 64.dp,
                circleColor = MaterialTheme.colorScheme.primary,
                checkColor = MaterialTheme.colorScheme.onPrimary
            )
        },
        title = { Text(title, fontWeight = FontWeight.SemiBold) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onPrimaryAction) { Text(primaryActionLabel) }
        },
        dismissButton = {
            if (secondaryActionLabel != null && onSecondaryAction != null) {
                TextButton(onClick = onSecondaryAction) { Text(secondaryActionLabel) }
            }
        }
    )
}

// ---------- Usage notes ----------
// In navigation, call SuccessScreen(
//   title = "Order placed",
//   message = "We'll notify you when it ships.",
//   primaryActionLabel = "Track order",
//   onPrimaryAction = { navController.navigate("track") },
//   secondaryActionLabel = "Back to home",
//   onSecondaryAction = { navController.popBackStack() }
// )
