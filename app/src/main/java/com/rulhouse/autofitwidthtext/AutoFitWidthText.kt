package com.rulhouse.autofitwidthtext

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun AutoFitWidthText(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    modifier: Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.body1
) {
    var textStyleState by remember { mutableStateOf(textStyle) }
    var readyToDraw by remember { mutableStateOf(false) }

    DisposableEffect(text) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                textStyleState = textStyleState
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    Text(
        modifier = modifier
            .drawWithContent {
                if (readyToDraw) drawContent()
            },
        text = text,
        style = textStyleState,
        softWrap = false,
        maxLines = 1,
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.didOverflowWidth) {
                textStyleState = textStyleState.copy(fontSize = textStyleState.fontSize * 0.9)
            } else {
                readyToDraw = true
            }
        }
    )
}