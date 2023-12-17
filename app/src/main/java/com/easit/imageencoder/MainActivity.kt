package com.easit.imageencoder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.easit.imageencoder.ui.theme.ImageEncoderTheme
import androidx.compose.ui.tooling.preview.Preview
import com.easit.imageencoder.ui.Encoder1
import com.easit.imageencoder.ui.Encoder101
import com.easit.imageencoder.ui.Encoder2
import com.easit.imageencoder.ui.Encoder3
import com.easit.imageencoder.ui.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageEncoderTheme {
                //Encoder1()
                //Encoder2()
                //Encoder3()
                Encoder101()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageEncoderPreview() {
    ImageEncoderTheme {
        //MainScreen()
    }
}