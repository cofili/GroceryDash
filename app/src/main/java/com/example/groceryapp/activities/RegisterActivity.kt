package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.App.Endpoints
import com.example.groceryapp.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.Edittext_email
import kotlinx.android.synthetic.main.activity_register.Edittext_password
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject
import java.lang.reflect.Method

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setupToolbar()
        init()
    }

    private fun setupToolbar() {
        toolbar.title = "Register"
        setSupportActionBar(toolbar)
    }

    private fun init() {
        button_register.setOnClickListener {
            var firstName : String = Edittext_name.text.toString()
            var email : String = Edittext_email.text.toString()
            var password : String = Edittext_password.text.toString()
            var mobile : String = Edittext_mobile.text.toString()

            var params = HashMap<String, String>()
            params["firstName"] = firstName
            params["email"] = email
            params["password"] = password
            params["mobile"] = mobile

            var jsonObject = JSONObject(params as Map<*, *>)

            var request = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.getRegister(),
                jsonObject,
                Response.Listener {
                    Toast.makeText(applicationContext, "Successfully Registered!", Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
            )
            Volley.newRequestQueue(this).add(request)

            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)



        }

    }
}