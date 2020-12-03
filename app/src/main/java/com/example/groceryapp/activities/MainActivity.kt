package com.example.groceryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.App.Endpoints
import com.example.groceryapp.activities.CartActivity
import com.example.groceryapp.adapters.AdapterCategory
import com.example.groceryapp.models.Category
import com.example.groceryapp.models.CategoryResponse
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.main_content.*
import kotlinx.android.synthetic.main.nav_header.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    var mList: ArrayList<Category> = ArrayList()
    lateinit var adapterCategory: AdapterCategory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        setupToolbar()
        getData()

        drawerLayout = drawer_grocery
        navView = nav_view
        var headerView = navView.getHeaderView(0)
        headerView.text_view_header_name.text = "Tony" //value will come from sharePref
        headerView.text_view_header_email.text = "tony@gmail.com"

        var toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        adapterCategory = AdapterCategory(this)
        recycler_view.adapter = adapterCategory
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL , false)
    }

    private fun setupToolbar() {
        toolbar.title = "Home"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun getData() {
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getCategory(),
            Response.Listener {
                Log.d("abc", it)
                progress_bar.visibility = View.GONE
                var gson = Gson()
                var categoryResponse = gson.fromJson(it, CategoryResponse::class.java)
                adapterCategory.setData(categoryResponse.data)

            },
            Response.ErrorListener {
    //            Log.d("abc", "error:" + it.networkResponse.statusCode)
                //Toast.makeText(applicationContext, it.message.toString(), Toast.LENGTH_SHORT).show()
            })
        requestQueue.add(request)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.menu_cart -> { startActivity(Intent(this, CartActivity::class.java))
                Toast.makeText(this, "Cart", Toast.LENGTH_SHORT).show()}
            R.id.menu_settings -> Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
            R.id.menu_logout -> Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_category -> Toast.makeText(applicationContext, "category", Toast.LENGTH_SHORT).show()
            R.id.item_Logout -> Toast.makeText(applicationContext, "Logout", Toast.LENGTH_SHORT).show()
            R.id.item_account -> Toast.makeText(applicationContext, "Account", Toast.LENGTH_SHORT).show()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }
    }
}