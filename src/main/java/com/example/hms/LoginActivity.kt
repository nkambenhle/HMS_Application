package com.example.hms

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import org.json.JSONException
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class LoginActivity : AppCompatActivity() {

    // Global variables for token and user ID
    companion object {
        var token: String? = null
        var userId: String? = null
    }

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            loginUser()
        }

        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun loginUser() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        val json = JSONObject()
        json.put("Username", username)
        json.put("Password", password)

        val client = OkHttpClient()
        val requestBody = json.toString().toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url("https://techcelestialsapp-fdhua4hhdwb9f4dc.southafricanorth-01.azurewebsites.net/auth/login")
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()

                    if (responseData != null) {
                        try {
                            val jsonResponse = JSONObject(responseData)
                            val token = jsonResponse.getString("token")
                            val userId = jsonResponse.getString("User_ID") // Correct key for user ID

                            // Store token and userId in the global variables
                            LoginActivity.token = token
                            LoginActivity.userId = userId

                            runOnUiThread {
                                Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@LoginActivity, MenuActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } catch (e: JSONException) {
                            runOnUiThread {
                                Toast.makeText(this@LoginActivity, "Response parsing error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@LoginActivity, "Login failed: Empty response", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    val responseBody = response.body?.string()
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "Login failed: ${response.message}\nResponse: $responseBody", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

}
