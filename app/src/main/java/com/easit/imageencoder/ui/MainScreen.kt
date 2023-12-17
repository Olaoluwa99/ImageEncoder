package com.easit.imageencoder.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
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
import java.io.IOException

@Composable
fun MainScreen() {

    /*

    //
    val context = LocalContext.current

    //
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val scaledBitmap = remember { mutableStateOf<Bitmap?>(null) }

    //
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    //
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    fun scaleImage(btm: Bitmap) {
        scaledBitmap.value = Bitmap.createScaledBitmap(btm, btm.width, btm.height, false)
    }

    fun splitImage(btm: Bitmap){
        //
        var widthParam = 0
        var heightParam = 0
        val heightExtend = btm.height/4
        val widthExtend = btm.width/4

        //Line 1
        widthParam = 0
        heightParam = 0
        val btmLn1Crp1 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)
        widthParam += widthExtend
        saveImageToInternalStorage("btmLn1Crp1", context, btmLn1Crp1)
        val btmLn1Crp2 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)
        widthParam += widthExtend
        saveImageToInternalStorage("btmLn1Crp2", context, btmLn1Crp2)
        val btmLn1Crp3 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)
        widthParam += widthExtend
        saveImageToInternalStorage("btmLn1Crp3", context, btmLn1Crp3)
        val btmLn1Crp4 = Bitmap.createBitmap(btm, widthParam, heightParam, widthExtend, heightExtend)
        saveImageToInternalStorage("btmLn1Crp4", context, btmLn1Crp4)


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
    }



    //START
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(state = ScrollState(0)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        //
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


        //RESULT
        Text(
            text = "Result",
            fontWeight = FontWeight.Bold,
            fontSize =20.sp,
            modifier = Modifier.fillMaxWidth()
        )
        //
        selectedImageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let { btmMain ->
                Image(
                    bitmap = btmMain.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(20.dp)
                )
                Text(text = "W=${bitmap.value!!.width}, H=${bitmap.value!!.height}")
                scaleImage(bitmap.value!!)
                /*
                AsyncImage(
                    model = btmMain,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                    )*/
                //splitImage(bitmap.value!!)
            }
            scaledBitmap.value?.let { btmMain ->
                Image(
                    bitmap = btmMain.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(20.dp)
                )
                Text(text = "W=${scaledBitmap.value!!.width}, H=${scaledBitmap.value!!.height}")
            }
        }
    }
}

fun saveImageToInternalStorageOther(id: String, context: Context, save: Bitmap) {
    val outputStream = context.openFileOutput("$id.png", Context.MODE_PRIVATE)
    save.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    outputStream.close()
}
private fun saveImageToInternalStorage(id: String, context: Context, bmpW: Bitmap){
    openFileOutput("CUT.${id}.png", ComponentActivity.MODE_PRIVATE).use { stream ->
        if (!bmpW.compress(Bitmap.CompressFormat.PNG, 100, stream)){
            Toast.makeText(context,"Couldn't save bitmap.", Toast.LENGTH_SHORT).show()
            throw IOException("Couldn't save bitmap.")
        }else{
            Toast.makeText(context,"Saved bitmap.", Toast.LENGTH_SHORT).show()
        }
    }*/
}