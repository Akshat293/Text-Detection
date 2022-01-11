package com.example.textdetection

import android.Manifest.permission_group.CAMERA
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat.startActivityForResult

import android.provider.MediaStore

import android.content.Intent
import android.widget.Toast

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnFailureListener

import com.google.firebase.ml.vision.text.FirebaseVisionText

import com.google.android.gms.tasks.OnSuccessListener

import com.google.firebase.ml.vision.FirebaseVision

import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector


class ScannerActivity : AppCompatActivity() {
    lateinit var imageBitmap:Bitmap
    lateinit var IMG:ImageView
    lateinit var Res:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        val But1: ImageView =findViewById(R.id.entry1)
        val But2: ImageView =findViewById(R.id.entry2)
         IMG=findViewById(R.id.Scanner1)
         Res=findViewById(R.id.Intro3)

        But1.setOnClickListener(){
      // Snap It
            dispatchTakePictureIntent()
        }
        But2.setOnClickListener(){
         // Display Result
            detectText()

        }
    }
    val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {
        // in the method we are displaying an intent to capture our image.
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // on below line we are calling a start activity
        // for result method to get the image captured.
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }
     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // calling on activity result method.
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // on below line we are getting
            // data from our bundles. .
            val extras = data?.extras
            imageBitmap = (extras!!["data"] as Bitmap?)!!

            // below line is to set the
            // image bitmap to our image.

            IMG.setImageBitmap(imageBitmap)
        }
    }
    private fun detectText() {
        val image = FirebaseVisionImage.fromBitmap(imageBitmap)

        // below line is to create a variable for detector and we
        // are getting vision text detector from our firebase vision.
       
        // below line is to create a variable for detector and we
        // are getting vision text detector from our firebase vision.
        val detector: FirebaseVisionTextDetector = FirebaseVision.getInstance().getVisionTextDetector()

        // adding on success listener method to detect the text from image.

        // adding on success listener method to detect the text from image.
        detector.detectInImage(image)
            .addOnSuccessListener(OnSuccessListener<FirebaseVisionText?> { firebaseVisionText -> // calling a method to process
                // our text after extracting.
                processTxt(firebaseVisionText)
            }).addOnFailureListener(OnFailureListener { // handling an error listener.
                Toast.makeText(
                    this,
                    "Fail to detect the text from image..",
                    Toast.LENGTH_SHORT
                ).show()
            })
    }
    private fun processTxt(text: FirebaseVisionText) {
        // below line is to create a list of vision blocks which
        // we will get from our firebase vision text.
        val blocks = text.blocks

        // checking if the size of the
        // block is not equal to zero.
        if (blocks.size == 0) {
            // if the size of blocks is zero then we are displaying
            // a toast message as no text detected.
            Toast.makeText(this, "No Text ", Toast.LENGTH_LONG).show()
            return
        }
        // extracting data from each block using a for loop.
        for (block in text.blocks) {
            // below line is to get text
            // from each block.
            val txt = block.text

            // below line is to set our
            // string to our text view.
            Res.setText(txt)
        }
    }
}