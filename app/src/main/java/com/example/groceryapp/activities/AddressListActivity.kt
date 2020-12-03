package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.App.Endpoints
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterAddress
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.models.AddressResponse
import com.example.groceryapp.models.SubCategoryResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_address_list.*
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_sub_category.*

class AddressListActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    lateinit var adapterAddress: AdapterAddress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_list)

        init()
    }

    private fun init() {
        adapterAddress = AdapterAddress(this)
        recycler_view_address.adapter = adapterAddress
        recycler_view_address.layoutManager = LinearLayoutManager(this)

        getData()

        button_addaddress.setOnClickListener {
            startActivity(Intent(this, AddAddressActivity::class.java))
        }
    }

    private fun getData() {
        sessionManager = SessionManager(this)

        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getAddressById(sessionManager.getUser()._id),
            Response.Listener {
                var gson = Gson()
                var addressResponse = gson.fromJson(it, AddressResponse::class.java)
                adapterAddress.setData(addressResponse.data)

            },
            Response.ErrorListener {   Log.d("abc", "error:" + it.networkResponse.statusCode) }
        )
        Volley.newRequestQueue(this).add(request)
    }
}