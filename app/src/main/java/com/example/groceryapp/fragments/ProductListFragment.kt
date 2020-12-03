package com.example.groceryapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.App.Endpoints
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterProduct
import com.example.groceryapp.models.ProductResponse
import com.example.groceryapp.models.SubCategory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.fragment_product_list.view.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var subId: Int = 0
    lateinit var adapterProduct: AdapterProduct

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subId = it.getInt(SubCategory.KEY_SUB_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_product_list, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {

        adapterProduct = AdapterProduct(activity!!)
        view.recyclerview_fragment.adapter = adapterProduct
        view.recyclerview_fragment.layoutManager = LinearLayoutManager(activity)

        getData()
    }

    private fun getData() {
        var requestQueue = Volley.newRequestQueue(activity)
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getProductBySubId(subId),
            Response.Listener {
                Log.d("abc", it)
                fragment_progressbar.visibility = View.GONE
                var gson = Gson()
                var productResponse = gson.fromJson(it, ProductResponse::class.java)
                adapterProduct.setData(productResponse.data)
            },
            Response.ErrorListener {
                Log.d("abc", "error:" + it.networkResponse.statusCode)
            }
        )
        requestQueue.add(request)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Int) =
            ProductListFragment().apply {
                arguments = Bundle().apply {
                    putInt(SubCategory.KEY_SUB_ID, param1)
                }
            }
    }
}