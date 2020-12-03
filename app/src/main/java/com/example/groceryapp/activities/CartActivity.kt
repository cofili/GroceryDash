package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterCart
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.models.Category
import com.example.groceryapp.models.Product
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.row_cart_adapter.*

class CartActivity : AppCompatActivity(), AdapterCart.OnAdapterListener {

    lateinit var adapterCart: AdapterCart
    lateinit var dbHelper: DBHelper
    var cartItems: ArrayList<Product> = ArrayList()
    var listener: AdapterCart.OnAdapterListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        dbHelper = DBHelper(this)

        init()
    }

    private fun init() {
        setupToolbar()
        //getData()

        cartItems = dbHelper.readItems()

        adapterCart = AdapterCart(this)
        adapterCart.setOnAdapterListener(this)
        recycler_view_cart.adapter = adapterCart
        recycler_view_cart.layoutManager = LinearLayoutManager(this)
        adapterCart.setData(cartItems)

        updateUI()
        button_checkout.setOnClickListener {
            startActivity(Intent(this, AddressListActivity::class.java))
        }

    }

    private fun updateUI() {
        text_amount_discount.text = "$" + dbHelper.Discount().toString()
        text_amount_subtotal.text = "$" + dbHelper.SubTotal().toString()
        text_amount_total.text = "$" + dbHelper.Total().toString()

    }

    private fun setupToolbar() {
        toolbar.title = "Shopping Cart"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()

        return true
    }

    override fun onButtonClicked(view: View, position: Int) {

        dbHelper.SubTotal()
        dbHelper.Discount()
        dbHelper.Total()

        when(view.id){
            R.id.button_add -> {
                var product = cartItems.get(position)
                dbHelper.increaseQuantity(product)
                adapterCart.notifyItemChanged(position)
                updateUI()

            }
            R.id.button_remove -> {
                var product = cartItems.get(position)
                dbHelper.reduceQuantity(product)
                adapterCart.notifyItemChanged(position)
                updateUI()

            }
        }
    }
}