package com.example.groceryapp

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.App.Config
import com.example.groceryapp.App.Endpoints
import com.example.groceryapp.activities.CartActivity
import com.example.groceryapp.adapters.AdapterCart
import com.example.groceryapp.adapters.AdapterCategory
import com.example.groceryapp.adapters.AdapterProduct
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.models.CategoryResponse
import com.example.groceryapp.models.Product
import com.example.groceryapp.models.ProductResponse
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.activity_product_detail.text_view_msrp
import kotlinx.android.synthetic.main.activity_product_detail.text_view_price
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.row_cart_adapter.*
import kotlinx.android.synthetic.main.row_grocery_adapter.view.*
import kotlinx.android.synthetic.main.row_product_adapter.*
import kotlinx.android.synthetic.main.row_product_adapter.view.*

class ProductDetailActivity : AppCompatActivity() {

    var product : Product? = null
    lateinit var adapterProduct: AdapterProduct
    //var dbHelper = DBHelper(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        product = intent.getSerializableExtra(Product.KEY_PRODUCT) as Product


        Picasso
            .get()
            .load("${Config.IMAGE_URL + product!!.image}")
            .into(image_view_detail)

        text_view_title.text = product?.productName
        text_view_description.text = product?.description
        text_view_msrp.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        text_view_msrp.text = "$" + product?.mrp.toString()
        text_view_price.text = "$" + product?.price.toString()


        init()
    }

    private fun init() {
        setupToolbar()
        adapterProduct = AdapterProduct(this)


        Button_add_tocart.setOnClickListener {

            var dbHelper = DBHelper(this)
            dbHelper.addProduct(product!!)
            startActivity(Intent(this, CartActivity::class.java))
        }
    }



    private fun setupToolbar() {
        toolbar.title = product?.productName
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                Toast.makeText(this, "Cart", Toast.LENGTH_SHORT).show()
            }
            R.id.menu_settings -> Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show()
            R.id.menu_logout -> Toast.makeText(this, "Lagout", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}

