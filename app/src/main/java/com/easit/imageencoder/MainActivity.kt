package com.easit.imageencoder

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.easit.imageencoder.ui.theme.ImageEncoderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageEncoderTheme {
                //
                var selectedImageUri by remember {
                    mutableStateOf<Uri?>(null)
                }

                //
                val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia(),
                    onResult = { uri -> selectedImageUri = uri }
                )

                //
                val btm = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
                val btmScd = Bitmap.createScaledBitmap(btm, 30, 30, false)
                val btmScd2 = Bitmap.createScaledBitmap(btm, btm.width/2, btm.height/2, false)
                //val btmCrp = Bitmap.createBitmap(btm,15, 15, 30, 30)

                //
                var widthParam = 0
                var heightParam = 0
                val heightExtend = btmScd2.height
                val widthExtend = btmScd2.width

                //Line 1
                widthParam = 0
                heightParam = 0
                val btmLn1Crp1 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)
                widthParam += widthExtend
                val btmLn1Crp2 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)
                widthParam += widthExtend
                val btmLn1Crp3 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)
                widthParam += widthExtend
                val btmLn1Crp4 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)


                //Line 2
                widthParam = 0
                heightParam += heightExtend
                val btmLn2Crp1 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)
                widthParam += widthExtend
                val btmLn2Crp2 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)
                widthParam += widthExtend
                val btmLn2Crp3 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)
                widthParam += widthExtend
                val btmLn2Crp4 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)


                //Line 3
                widthParam = 0
                heightParam += heightExtend
                val btmLn3Crp1 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)
                widthParam += widthExtend
                val btmLn3Crp2 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)
                widthParam += widthExtend
                val btmLn3Crp3 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)
                widthParam += widthExtend
                val btmLn3Crp4 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)


                //Line 4
                widthParam = 0
                heightParam += heightExtend
                val btmLn4Crp1 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)
                widthParam += widthExtend
                val btmLn4Crp2 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)
                widthParam += widthExtend
                val btmLn4Crp3 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)
                widthParam += widthExtend
                val btmLn4Crp4 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)



                //
                Column() {
                    //
                    Button(onClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) {
                        Text(text = "Pick one photo")
                    }
                    
                    

                    //
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop,

                    )

                }


            }
        }
    }
}


//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ImageEncoderTheme {
//        Greeting("Android")
//    }
//}