package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.App.Endpoints
import com.example.groceryapp.MainActivity
import com.example.groceryapp.R
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.models.User
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupToolbar()
        init()
    }

    private fun setupToolbar() {
        toolbar.title = "Login"
        setSupportActionBar(toolbar)
    }

    private fun init() {
        button_login.setOnClickListener {
            var email : String = Edittext_email.text.toString()
            var password : String = Edittext_password.text.toString()

            var params = HashMap<String, String>()
            params["email"] = email
            params["password"] = password

            var jsonObject = JSONObject(params as Map<*, *>)

            var request = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.getLogin(),
                jsonObject,
                Response.Listener {
                    // get user JSON Object
                    var userString = it.getString("user")
                    //convert JSON obect to user object
                    var userobject = Gson().fromJson(userString, User::class.java)
                    sessionManager = SessionManager(this)
                    sessionManager.addUser(userobject)
                    Toast.makeText(applicationContext, "Successfully Logged In!", Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
            )
            Volley.newRequestQueue(this).add(request)

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
        register_from_login.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}