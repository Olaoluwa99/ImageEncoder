package com.easit.imageencoder

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.easit.imageencoder.ui.theme.ImageEncoderTheme
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageEncoderTheme {
                //
                val context = LocalContext.current

                //
                val bitmap = remember { mutableStateOf<Bitmap?>(null) }

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
                val myBtm = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
                val btmScd = Bitmap.createScaledBitmap(myBtm, 30, 30, false)
                val btmScd2 = Bitmap.createScaledBitmap(myBtm, myBtm.width/2, myBtm.height/2, false)
                //val btmCrp = Bitmap.createBitmap(btm,15, 15, 30, 30)

//                //
//                fun saveImageToInternalStorageY(id: String, context: Context, save: Bitmap) {
//                    val inputStream = context.contentResolver.openInputStream(save)
//                    val outputStream = context.openFileOutput("$id.jpg", Context.MODE_PRIVATE)
//                    inputStream?.use { input ->
//                        outputStream.use { output ->
//                            input.copyTo(output)
//                        }
//                    }
//                }

                //
                fun saveImageToInternalStorage(id: String, context: Context, save: Bitmap) {
                    val outputStream = context.openFileOutput("$id.jpg", Context.MODE_PRIVATE)
                    save.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    outputStream.close()
                }

                fun by (btm: Bitmap){
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
                    selectedImageUri?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            bitmap.value = MediaStore.Images
                                .Media.getBitmap(context.contentResolver, it)
                        } else {
                            val source = ImageDecoder.createSource(context.contentResolver, it)
                            bitmap.value = ImageDecoder.decodeBitmap(source)
                        }

                        bitmap.value?.let { btmMain ->
                            /*
                            Image(
                                bitmap = btm.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(400.dp)
                                    .padding(20.dp)
                            )*/
                            AsyncImage(
                                model = btmMain.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth(),
                                contentScale = ContentScale.Crop,
                                )
                            //
                            by(btmMain)
                        }
                    }
                    
                    

                    //


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