package com.example.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.App.Config
import com.example.groceryapp.ProductDetailActivity
import com.example.groceryapp.R
import com.example.groceryapp.SubCategoryActivity
import com.example.groceryapp.models.Category
import com.example.groceryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_product_adapter.view.*

class AdapterProduct (var mContext: Context): RecyclerView.Adapter<AdapterProduct.ViewHolder>() {

    var mList: ArrayList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(mContext).inflate(R.layout.row_product_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product = mList[position]
        holder.bind(product)

    }

    fun setData(data: ArrayList<Product>) {
        mList = data
        notifyDataSetChanged()

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product) {
            itemView.text_view.text = product.productName
            itemView.text_view_price.text = "$" +  product.price.toString()
            itemView.text_view_msrp.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.text_view_msrp.text = "$" + product.mrp.toString()

            Picasso
                .get()
                .load("${Config.IMAGE_URL + product.image}")
                .into(itemView.image_view)

            itemView.setOnClickListener {
                var intent = Intent(mContext, ProductDetailActivity::class.java)
                intent.putExtra(Product.KEY_PRODUCT, product)
                mContext.startActivity(intent)

            }
        }

    }
}