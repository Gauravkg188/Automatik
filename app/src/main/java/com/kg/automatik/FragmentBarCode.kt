package com.kg.automatik


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage


class FragmentBarCode(): Fragment(R.layout.fragment_barcode) {


    private val pickImage=101;
    lateinit var image: ImageView
    lateinit var text: TextView

    private lateinit var imageUri: Uri


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        image=view.findViewById(R.id.image)
        text=view.findViewById(R.id.textImage)




        image.setOnClickListener(View.OnClickListener {


            try {
                text.text=""
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



        val image: InputImage = InputImage.fromFilePath(context!!, imageUri)


        val scanner = BarcodeScanning.getClient()
        scanner.process(image).addOnSuccessListener { barcodes->

            for(barcode in barcodes) {


                Log.d("kitty", "processing: "+barcode.displayValue)
                when(barcode.valueType)
                {

                    Barcode.TYPE_WIFI -> {
                        val ssid = barcode.wifi!!.ssid
                        val password = barcode.wifi!!.password
                        val type = barcode.wifi!!.encryptionType
                        var result=StringBuilder()
                         result.append("Wifi Information\n")
                        result.append("Id:$ssid").append("\n").append("Password:$password\n").append("Type:$type\n")
                        text.text=result.toString()


                    }
                    Barcode.TYPE_URL -> {
                        val title = barcode.url!!.title
                        val url = barcode.url!!.url
                        var result=StringBuilder()
                        result.append("Url Information\n")
                        result.append("Title:$title\n").append("Url:$url\n")
                        text.text=result.toString()
                    }
                    Barcode.TYPE_PHONE->{

                        val number="Number:"+barcode.phone!!.number
                        text.text=number

                    }
                    else->{

                        val result=barcode.rawValue
                        text.text=result
                    }


                }


            }


        }





        }



    }











