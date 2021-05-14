package com.example.ml_kit_textrecognition_example.ui.main

import android.app.Activity.RESULT_OK
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ml_kit_textrecognition_example.databinding.MainFragmentBinding
import java.io.File


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainFragmentBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)

        binding.analyzeImageButton.setOnClickListener{ clicked() }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun clicked(){
        openImageIntent()
    }

    private var outputFileUri: Uri? = null

    private fun openImageIntent() {

        // Determine Uri of camera image to save.
        val root =
            File(Environment.getExternalStorageState() + File.separator.toString() + "MyDir" + File.separator)
        root.mkdirs()
        val fname: String = System.currentTimeMillis().toString() + ".jpg"
        val sdImageMainDirectory = File(root, fname)
        outputFileUri = Uri.fromFile(sdImageMainDirectory)

        // Camera.
        val cameraIntents: MutableList<Intent> = ArrayList()
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val packageManager: PackageManager = context!!.packageManager
        val listCam = packageManager.queryIntentActivities(captureIntent, 0)
        for (res in listCam) {
            val packageName = res.activityInfo.packageName
            val intent = Intent(captureIntent)
            intent.component = ComponentName(packageName, res.activityInfo.name)
            intent.setPackage(packageName)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
            cameraIntents.add(intent)
        }

        // Filesystem.
        val galleryIntent = Intent()
        galleryIntent.type = "image/*"
        galleryIntent.action = Intent.ACTION_GET_CONTENT

        // Chooser of filesystem options.
        val chooserIntent = Intent.createChooser(galleryIntent, "Select Source")

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toTypedArray())
        startActivityForResult(chooserIntent, 200)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 200) {
                val isCamera: Boolean
                isCamera = if (data == null) {
                    true
                } else {
                    val action = data.action
                    if (action == null) {
                        false
                    } else {
                        action == MediaStore.ACTION_IMAGE_CAPTURE
                    }
                }
                val selectedImageUri: Uri?
                selectedImageUri = if (isCamera) {
                    outputFileUri
                } else {
                    data?.data
                }
            }
        }
    }

}