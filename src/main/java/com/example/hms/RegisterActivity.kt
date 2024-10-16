package com.example.hms

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray


class RegisterActivity : AppCompatActivity() {

    // Declare member variables for the input fields
    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var roleEditText: EditText
    private lateinit var fieldEditText: EditText
    private lateinit var modulesEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize UI elements
        nameEditText = findViewById(R.id.name)
        surnameEditText = findViewById(R.id.surname)
        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        emailEditText = findViewById(R.id.email)
        roleEditText = findViewById(R.id.role)
        fieldEditText = findViewById(R.id.field)
        modulesEditText = findViewById(R.id.modules)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        // Get user input
        val name = nameEditText.text.toString().trim()
        val surname = surnameEditText.text.toString().trim()
        val username = usernameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val role = roleEditText.text.toString().trim()
        val field = fieldEditText.text.toString().trim()
        val modules = modulesEditText.text.toString().trim().split(",").map { it.trim() }

        // Validate input
        if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || password.isEmpty() ||
            email.isEmpty() || role.isEmpty() || field.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Build the JSON object for the request
        val json = JSONObject()
        json.put("Name", name)
        json.put("Surname", surname)
        json.put("Username", username)
        json.put("Password", password)
        json.put("Email", email)
        json.put("Role", role)
        json.put("Field", field)
        json.put("LoggedIn", false)

        // Convert the modules list to a JSONArray
        val jsonArray = JSONArray()
        for (module in modules) {
            jsonArray.put(module)
        }
        json.put("Modules", jsonArray)

        // Send the registration request
        val client = OkHttpClient()
        val requestBody = json.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder()
            .url("https://techcelestialsapp-fdhua4hhdwb9f4dc.southafricanorth-01.azurewebsites.net/auth/register")
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@RegisterActivity, "Registration failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterActivity, MenuActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    val responseBody = response.body?.string()
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Registration failed: ${responseBody}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

}
