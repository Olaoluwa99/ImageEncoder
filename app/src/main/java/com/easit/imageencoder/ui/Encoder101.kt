package com.easit.imageencoder.ui

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easit.imageencoder.data.RotateTileResult
import com.easit.imageencoder.model.EncoderModel1
import com.easit.imageencoder.model.EncoderModel2
import com.easit.imageencoder.model.EncoderModel3
import com.easit.imageencoder.model.ImageEncoder

@Composable
fun Encoder101() {
    val context = LocalContext.current

    val imagePartsRoot = 4
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var phase1Bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var phase2Bitmap by remember { mutableStateOf<Bitmap?>(null) }

    var decoded1Bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var decoded2Bitmap by remember { mutableStateOf<Bitmap?>(null) }

    val emptyBitmapList = mutableListOf<Bitmap>()
    val emptyIntList = mutableListOf<Int>()
    var rotatedResponse by remember { mutableStateOf(RotateTileResult(emptyIntList, emptyBitmapList)) }

    //
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    //
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    val userSeed = 1234567L


    //START
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        //
        item {
            Text(
                text = "Image Encoder",
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Instruction",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )

            //
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "1. Click 'Select image' to start")
                Text(text = "2. Accept all permissions.")
                Text(text = "3. Select the image to encode.")
            }
        }

        item {
            //
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }) {
                Text(text = "Select image")
            }


            Spacer(modifier = Modifier.height(24.dp))
            //RESULT
            Text(
                text = "Result",
                fontWeight = FontWeight.Bold,
                fontSize =20.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            //
            selectedImageUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap = MediaStore.Images
                        .Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    bitmap = ImageDecoder.decodeBitmap(source)
                }

                bitmap?.let { btmMain ->
                    Image(
                        bitmap = btmMain.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .padding(20.dp)
                    )
                    Text(text = "W=${bitmap!!.width}, H=${bitmap!!.height}")

                }
            }
        }

        item {
            //
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                val encoder = ImageEncoder()
                phase1Bitmap = encoder.reassembleImage(encoder.shuffleBitmap(encoder.splitImageToParts(imagePartsRoot, bitmap!!), userSeed))
            }) {
                Text(text = "Shuffle image grid")
            }
            //
            phase1Bitmap?.let { btmMain ->
                Image(
                    bitmap = btmMain.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(20.dp)
                )
            }
        }

        item {
            //
            Button(modifier = Modifier.fillMaxWidth(),onClick = {
                val encoder = ImageEncoder()
                val splitImageList = encoder.splitImageToParts(imagePartsRoot, bitmap!!)
                rotatedResponse = encoder.rotateTiles(splitImageList as MutableList<Bitmap>)
                val shuffledRotatedImages = encoder.shuffleBitmap(rotatedResponse.tile, userSeed)
                phase2Bitmap = encoder.reassembleImage(shuffledRotatedImages)
            }) {
                Text(text = "Shuffle & Rotate Image grid")
            }
            //
            phase2Bitmap?.let { btmMain ->
                Image(
                    bitmap = btmMain.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(20.dp)                )
            }
        }

        item {
            //
            Button(modifier = Modifier.fillMaxWidth(),onClick = {
                val decoder = com.easit.imageencoder.model.ImageDecoder()
                decoded1Bitmap = decoder.fullUnShuffle(phase1Bitmap!!, userSeed)
            }) {
                Text(text = "Decode - UnShuffle Image grid")
            }
            //
            decoded1Bitmap?.let { btmMain ->
                Image(
                    bitmap = btmMain.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(20.dp)
                )
            }
        }

        item {
            //
            Button(modifier = Modifier.fillMaxWidth(),onClick = {
                val decoder = com.easit.imageencoder.model.ImageDecoder()
                decoded2Bitmap = decoder.fullUnRotateAndUnShuffle(phase2Bitmap!!, userSeed, rotatedResponse.shuffledListIndex)
            }) {
                Text(text = "Decode - Shuffle & Rotate Image grid")
            }
            //
            decoded2Bitmap?.let { btmMain ->
                Image(
                    bitmap = btmMain.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(20.dp)
                )
            }
        }
    }
}