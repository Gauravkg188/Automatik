package com.kg.automatik

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions

import java.io.IOException


class FragmentImageLabeling :Fragment(R.layout.fragment_image_label) {



    private val pickImage=101;
    lateinit var image:ImageView
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: FragmentImageAdapter
     private lateinit var itemList:ArrayList<LabelModel>
     private lateinit var imageUri: Uri
    lateinit var addText:TextView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

           image=view.findViewById(R.id.image)
           addText=view.findViewById(R.id.addText)

           recyclerView=view.findViewById(R.id.recyclerView)
          adapter= FragmentImageAdapter(ArrayList())
          recyclerView.layoutManager=LinearLayoutManager(context,RecyclerView.VERTICAL,false)
          recyclerView.setHasFixedSize(true)
         recyclerView.adapter=adapter


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
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            if (data!=null)
            {
                if(addText.isVisible)addText.visibility = View.GONE
                imageUri = data.data!!
                image.setImageURI(imageUri)
                processing(imageUri)
            }

        }
    }

    fun processing(imageUri: Uri) {


       itemList=ArrayList()

        val image: InputImage = InputImage.fromFilePath(context!!, imageUri)



        val options = ImageLabelerOptions.Builder()
                      .setConfidenceThreshold(0.7f)
                     .build()
        val labeler = ImageLabeling.getClient(options)
        labeler.process(image)
            .addOnSuccessListener { labels ->



                    for (label in labels) {
                        val text = label.text
                        Log.d("kitty", "processing: "+text)
                        val confidence = label.confidence
                        var labelmodel= LabelModel(text,confidence.toString())
                        itemList.add(labelmodel)

                    }



                adapter.addList(itemList)
                adapter.notifyDataSetChanged()

            }

                        .addOnFailureListener { e ->
                            // Task failed with an exception
                            // ...
                        }




    }

}