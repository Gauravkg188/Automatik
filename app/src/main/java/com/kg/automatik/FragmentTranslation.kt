package com.kg.automatik

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class FragmentTranslation():Fragment(R.layout.fragment_translation) {


    lateinit var inputText:EditText
    lateinit var button:Button
    lateinit var translatedText:TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputText=view.findViewById(R.id.input_text)
        button=view.findViewById(R.id.button)
        translatedText=view.findViewById(R.id.translated_text)

        button.setOnClickListener(View.OnClickListener {

            val text=inputText.text.toString().trim()
            if(text.isNotEmpty())
            {
                val languageIdentifier = LanguageIdentification.getClient()
                languageIdentifier.identifyLanguage(text)
                    .addOnSuccessListener { languageCode ->
                        if (languageCode == "und") {
                            translatedText.text="Failed to detect language"
                        } else {

                            processing(languageCode,text)
                            Log.d("kitty", "onViewCreated: $languageCode")
                        }
                    }
                    .addOnFailureListener {
                        // Model couldn’t be loaded or other internal error.
                        // ...
                    }
            }

        })


    }

    private fun processing(languageIdentification: String, text:String)
    {


        var conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        val options = TranslateLanguage.fromLanguageTag(languageIdentification)?.let {
            TranslatorOptions.Builder()
                .setSourceLanguage(it)
                .setTargetLanguage(TranslateLanguage.ENGLISH)
                .build()
        }
        val translator = options?.let { Translation.getClient(it) }
        translator?.downloadModelIfNeeded(conditions)?.addOnSuccessListener {

            translator.translate(text).addOnSuccessListener {result->

                translatedText.text=result

            } }



          }

    }


