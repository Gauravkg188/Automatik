package com.kg.automatik

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.google.mlkit.vision.text.TextRecognition
import java.io.IOException

class FragmentImageToText(): Fragment(R.layout.fragment_image_text) {


    private val pickImage=101;
    lateinit var image: ImageView
    lateinit var text:TextView

    private lateinit var imageUri: Uri


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        image=view.findViewById(R.id.image)
        text=view.findViewById(R.id.textImage)



        image.setOnClickListener(View.OnClickListener {


            try {
                val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(gallery, pickImage)
            }
            catch (e:Exception)
            {

            }


        })









    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == pickImage) {
            if (data!=null)
            {
                imageUri = data.data!!
                image.setImageURI(imageUri)
                processing(imageUri)
            }

        }
    }

    private fun processing(imageUri: Uri) {



        val image: InputImage=InputImage.fromFilePath(context!!, imageUri)


        val recognizer = TextRecognition.getClient()

        recognizer.process(image).addOnSuccessListener {


                text.text=it.text


            }



        }




    }






