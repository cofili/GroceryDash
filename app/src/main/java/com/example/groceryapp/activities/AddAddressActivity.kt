package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.App.Endpoints
import com.example.groceryapp.R
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.models.Address
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.nav_header.view.*
import org.json.JSONObject

class AddAddressActivity : AppCompatActivity(){

    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        init()
    }

    private fun init() {
        setupToolbar()
         sessionManager = SessionManager(this)
        button_add_address.setOnClickListener {

            var city = edit_text_city.text.toString()
            var housenumber = edit_text_housenumber.text.toString()
            var zipcode = edit_text_zipcode.text.toString().toInt()
            var street = edit_text_Street.text.toString()
            var type = edit_text_Type.text.toString()


            var address = Address(city, housenumber, zipcode, street, type, sessionManager.getUser()._id )

            var jsonObject = JSONObject(Gson().toJson(address))
            var request = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.getAddress(),
                jsonObject,
                Response.Listener {
                    Toast.makeText(applicationContext, "Successfully Added!", Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
            )
            Volley.newRequestQueue(this).add(request)

            startActivity(Intent(this, AddressListActivity::class.java))

        }



    }


    private fun setupToolbar() {
        toolbar.title = "Add Address"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.menu_cart -> Toast.makeText(this, "Cart", Toast.LENGTH_SHORT).show()
            R.id.menu_settings -> Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
            R.id.menu_logout -> Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
        }
        return true
    }



}