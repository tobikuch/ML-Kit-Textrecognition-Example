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
        try {
            val image = InputImage.fromFilePath(context, path)
            val recognizer = TextRecognition.getClient()
            recognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        val blocksCache = mutableListOf<String>()
                        for (block in visionText.textBlocks) {
                            val blockText = block.text
                            Timber.i("BlockText: $blockText")
                            val blockLines = StringBuilder()
                            for (line in block.lines) {
                                blockLines.append(line.text + "\n")
                                Timber.i("LineText: ${line.text}")
                            }
                            blocksCache.add(blockLines.toString())
                        }
                        blocks.value = blocksCache
                    }
                    .addOnFailureListener { e ->
                        Timber.e(e)
                    }

        } catch (e: IOException) {
            Timber.e("IO Exception")
            e.printStackTrace()
        }
    }

}