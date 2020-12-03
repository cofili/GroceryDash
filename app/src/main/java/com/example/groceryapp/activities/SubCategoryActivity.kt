package com.example.groceryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.App.Endpoints
import com.example.groceryapp.activities.CartActivity
import com.example.groceryapp.adapters.AdapterFragment
import com.example.groceryapp.models.Category
import com.example.groceryapp.models.SubCategory
import com.example.groceryapp.models.SubCategoryResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.app_bar.*

class SubCategoryActivity : AppCompatActivity() {

    var category: Category? = null
    var subCategoryList: ArrayList<SubCategory> = ArrayList()
    lateinit var adapterFragment: AdapterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

        category = intent.getSerializableExtra(Category.KEY_CATEGORY) as Category

        Toast.makeText(this, category?.catName, Toast.LENGTH_SHORT).show()

        init()
    }

    private fun init() {
        setupToolbar()
        getData()
        adapterFragment = AdapterFragment(supportFragmentManager)
    }

    private fun setupToolbar() {
        toolbar.title = category?.catName
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getData() {
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getSubCategoryByCatId(category!!.catId),
            Response.Listener {
                var gson = Gson()
                var subCategoryResponse = gson.fromJson(it, SubCategoryResponse::class.java)
                subCategoryList.addAll(subCategoryResponse.data)

                for (i in 0 until subCategoryList.size) {
                    adapterFragment.AddFragment(subCategoryList[i])
                }
                view_pager.adapter = adapterFragment
                tab_layout.setupWithViewPager(view_pager)

            },
            Response.ErrorListener {   Log.d("abc", "error:" + it.networkResponse.statusCode) }
        )
        Volley.newRequestQueue(this).add(request)
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
            R.id.menu_logout -> Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}