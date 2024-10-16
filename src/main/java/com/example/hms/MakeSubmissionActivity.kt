package com.example.hms

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import java.io.File
import kotlin.concurrent.thread

class MakeSubmissionActivity : AppCompatActivity() {

    private lateinit var videoUri: Uri
    private lateinit var assignmentIdEditText: EditText
    private lateinit var userIdEditText: EditText
    private lateinit var uploadButton: Button
    private lateinit var videoView: VideoView
    private lateinit var recordVideoLauncher: ActivityResultLauncher<Intent>

    private val requestCodePermissions = 10
    private val requiredPermissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_submission)

        videoView = findViewById(R.id.videoView)
        assignmentIdEditText = findViewById(R.id.editTextAssignmentId)
        userIdEditText = findViewById(R.id.editTextUserId)
        val recordButton = findViewById<Button>(R.id.buttonRecordVideo)
        uploadButton = findViewById(R.id.buttonUploadVideo)

        // Retrieve the userId from LoginActivity (or wherever you stored it)
        val userId = LoginActivity.userId

        // Set the UserID automatically in the UserID EditText
        if (userId != null && userId.isNotEmpty()) {
            userIdEditText.setText(userId)
        } else {
            userIdEditText.setText("User ID not found")
        }

        // Set up the VideoView with MediaController
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        // Request permissions
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, requiredPermissions, requestCodePermissions)
        }

        // Set up the ActivityResultLauncher for recording videos
        recordVideoLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                videoUri = result.data?.data ?: return@registerForActivityResult
                videoView.setVideoURI(videoUri)
                videoView.start()
            } else {
                Toast.makeText(this, "Video capture failed", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up click listener for recording video
        recordButton.setOnClickListener {
            startVideo()
        }

        // Set up click listener for uploading the video
        uploadButton.setOnClickListener {
            if (::videoUri.isInitialized) {
                submitVideo(videoUri)  // Pass the Uri to the submit function
            } else {
                Toast.makeText(this, "No video selected", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun startVideo() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        recordVideoLauncher.launch(intent)
    }

    private fun submitVideo(videoUri: Uri) {
        val assignmentId = assignmentIdEditText.text.toString()
        val userId = userIdEditText.text.toString()

        if (assignmentId.isBlank() || userId.isBlank()) {
            Toast.makeText(this, "Assignment ID or User ID is missing", Toast.LENGTH_SHORT).show()
            return
        }

        // Prepare the request body
        val contentResolver = contentResolver
        val videoStream = contentResolver.openInputStream(videoUri)
        if (videoStream == null) {
            Toast.makeText(this, "Failed to open video file", Toast.LENGTH_SHORT).show()
            return
        }

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("Assignment_ID", assignmentId)
            .addFormDataPart("User_ID", userId)
            .addFormDataPart("video", "video.mp4",
                RequestBody.create("video/mp4".toMediaType(), videoStream.readBytes()))

            .build()


        val token = LoginActivity.token

        // Create a request to submit the video with a token
        val request = Request.Builder()
            .url("https://techcelestialsapp-fdhua4hhdwb9f4dc.southafricanorth-01.azurewebsites.net/submissions")
            .addHeader("Authorization", "Bearer $token")  // Add token if required
            .post(requestBody)
            .build()

        // Execute the request
        val client = OkHttpClient()
        thread {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    val errorBody = response.body?.string()
                    Log.e("Submission Error", "Error ${response.code}: $errorBody")
                    runOnUiThread {
                        Toast.makeText(this, "Error ${response.code}: $errorBody", Toast.LENGTH_LONG).show()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this, "Video submitted successfully!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }



    // Function to convert URI to file path
    private fun getRealPathFromURI(contentUri: Uri): String {
        var result: String? = null
        val cursor = contentResolver.query(contentUri, null, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val idx = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA)
                result = cursor.getString(idx)
            }
            cursor.close()
        }
        return result ?: ""
    }

    private fun allPermissionsGranted() = requiredPermissions.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
}
