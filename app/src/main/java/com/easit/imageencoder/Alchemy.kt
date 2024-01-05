package com.easit.imageencoder

import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun createComposedBitmapFromRectangle(bitmap: ImageBitmap): ImageBitmap? {
    var mutableBitmap: ImageBitmap? = null
    var rectStart by remember { mutableStateOf(Offset.Zero) }
    var rectEnd by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectGestures(
                    onStart = { offset ->
                        rectStart = offset
                    },
                    onDrag = { dragAmount, offset ->
                        rectEnd = rectStart + dragAmount
                        mutableBitmap = drawRectangleOnBitmap(bitmap, rectStart, rectEnd)
                    },
                    onFinish = {
                        if (mutableBitmap != null) {
                            // Do something with the new bitmap, e.g., show it
                            // replace null with your actual action
                            mutableBitmap = null
                        }
                    }
                )
                /*
                detectGestures(
                    onStart = { offset ->
                        rectStart = offset
                    },
                    onDrag = { dragAmount, offset ->
                        rectEnd = rectStart + dragAmount
                        mutableBitmap = drawRectangleOnBitmap(bitmap, rectStart, rectEnd)
                    },
                    onFinish = {
                        if (mutableBitmap != null) {
                            // Do something with the new bitmap, e.g., show it
                            // replace null with your actual action
                            mutableBitmap = null
                        }
                    }
                )*/
            }
    ) {
        if (mutableBitmap != null) {
            Image(
                bitmap = mutableBitmap!!,
                "",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Image(
                bitmap = bitmap,
                "",
                modifier = Modifier.fillMaxSize()
            )
        }
    }


    return mutableBitmap
}

private fun detectTouches() {
    val down = awaitPointerEventScope {
        awaitFirstDown()
    }

    var dragAmount by remember { mutableStateOf(Offset.Zero) }

    val drag = awaitPointerEventScope {
        horizontalDrag(down.id) { change ->
            dragAmount += change.positionChange()
        }
    }

    val up = awaitPointerEventScope {
        awaitDragOrCancellation(down.id)
    }

    // Handle touch events based on down, drag, and up actions
    // (similar to the previous code with forEachTouch)
}

private fun drawRectangleOnBitmap(
    bitmap: ImageBitmap,
    rectStart: Offset,
    rectEnd: Offset
): ImageBitmap? {
    val newBitmap = ImageBitmap.create(bitmap.width, bitmap.height)
    val canvas = Canvas(newBitmap)
    val paint = Paint().apply {
        color = Color.Red.toArgb()
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }
    canvas.drawRect(
        rectStart.x,
        rectStart.y,
        rectEnd.x,
        rectEnd.y,
        paint
    )
    return newBitmap
}

private fun detectGestures(
    onStart: (Offset) -> Unit,
    onDrag: (Offset, Offset) -> Unit,
    onFinish: () -> Unit
) {
    forEachG
    forEachTouch { event ->
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> onStart(event.getTouchPoint())
            MotionEvent.ACTION_MOVE -> onDrag(event.getTouchPoint(), event.previousTouchPoint)
            MotionEvent.ACTION_UP -> onFinish()
        }
    }
}

private fun detectGestures2(
    onStart: (Offset) -> Unit,
    onDrag: (Offset, Offset) -> Unit,
    onFinish: () -> Unit
) {
    // Use awaitPointerEventScope for gesture handling
    AwaitPointerEventScope {
        awaitFirstDown().also {
            onStart(it.position)
        }
        // Track subsequent drag events
        awaitDragOrCancellation().also {
            val dragAmount = it.positionChange()
            onDrag(dragAmount, it.position)
        }
        // Handle gesture completion
        awaitUpOrCancellation().also {
            onFinish()
        }
    }
}