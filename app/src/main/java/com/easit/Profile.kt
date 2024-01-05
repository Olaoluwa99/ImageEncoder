package com.easit

import android.graphics.Typeface
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.easit.imageencoder.fullImageProcess
import com.easit.imageencoder.getProfileById

@Composable
fun Profile() {

    val profileImageBitmap2 = fullImageProcess(
        text = "IO", width = 200, height = 200, startColor = Color.DarkGray, endColor = Color.LightGray,
        textColor = Color.White, textSize = 100f, typeface = Typeface.MONOSPACE, 123456
    )
    //0xFF42A5F5
    val profileImageBitmap = getProfileById("IO0xFFFFFF000xFFFF00FF123456")

    Column {
        Image(
            bitmap = profileImageBitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .padding(20.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(35.dp)),
            contentAlignment = Alignment.Center
        ){
            Image(
                bitmap = profileImageBitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
            )
        }
    }
}