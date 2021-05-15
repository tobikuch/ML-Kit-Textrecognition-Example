package com.example.ml_kit_textrecognition_example.ui.main

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import timber.log.Timber
import java.io.IOException

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    fun recognizeText(path: Uri, context: Context){
        val image: InputImage
        Timber.i("Image Path $path")
        try {
            image = InputImage.fromFilePath(context, path)

            val recognizer = TextRecognition.getClient()

            val result = recognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        Timber.i("Recognize Success")
                    }
                    .addOnFailureListener { e ->
                        Timber.e(e)
                        // Task failed with an exception
                        // ...
                    }
        } catch (e: IOException) {
            Timber.e("IO Exception")
            e.printStackTrace()
        }
    }

}