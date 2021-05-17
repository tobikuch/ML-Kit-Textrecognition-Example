package com.example.ml_kit_textrecognition_example.ui.main

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import timber.log.Timber
import java.io.IOException

class MainViewModel : ViewModel() {
    val blocks = MutableLiveData<List<String>>()

    fun recognizeText(path: Uri, context: Context){
        val image: InputImage
        Timber.i("Image Path $path")
        try {
            image = InputImage.fromFilePath(context, path)

            val recognizer = TextRecognition.getClient()
            val result = recognizer.process(image)
                    .addOnSuccessListener { visionText ->

                        val blocksCache = mutableListOf<String>()
                        val resultText = visionText.text
                        for (block in visionText.textBlocks) {
                            val blockText = block.text
                            Timber.i("BlockText: $blockText")
                            val blockCornerPoints = block.cornerPoints
                            val blockFrame = block.boundingBox
                            val blockLines = StringBuilder()
                            for (line in block.lines) {
                                val lineText = line.text
                                blockLines.append(lineText + "\n")
                                Timber.i("LineText: $lineText")
                                val lineCornerPoints = line.cornerPoints
                                val lineFrame = line.boundingBox
                                for (element in line.elements) {
                                    val elementText = element.text
                                    //Timber.i("ElementText: $elementText")
                                    val elementCornerPoints = element.cornerPoints
                                    val elementFrame = element.boundingBox
                                }
                            }
                            blocksCache.add(blockLines.toString())
                        }
                        blocks.value = blocksCache
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