package com.example.groceryapp.adapters


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import  android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.groceryapp.App.Config
import com.example.groceryapp.R
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.models.Product
import com.squareup.picasso.Picasso
import com.example.groceryapp.activities.CartActivity
import kotlinx.android.synthetic.main.row_cart_adapter.view.*


class AdapterCart(var mContext: Context) : RecyclerView.Adapter<AdapterCart.ViewHolder>() {
    var mList: ArrayList<Product> = ArrayList()
    var dbHelper = DBHelper(mContext)
    var listener: OnAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCart.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_cart_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product = mList[position]
        holder.bind(product, position)
    }


    fun setData(data: ArrayList<Product>) {
        mList = data
        Log.d("abc", mList.toString())
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int){
        var product = mList.get(position)
        dbHelper.deleteProduct(product._id.toString())
        mList.removeAt(position)
        notifyDataSetChanged()
        Log.d("abc", mList.toString())

    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(product: Product, position: Int) {

            itemView.text_view_item.text = product.productName
            itemView.text_view_price.text = "$"+ product.price.toString()
            itemView.text_view_msrp.text = "$" + product.mrp.toString()
            itemView.counter.text = dbHelper.getCurrentQuantity(product).toString()
            Log.d("abc", "${dbHelper.getCurrentQuantity(product)}")

            Picasso
                .get()
                .load("${Config.IMAGE_URL + product.image}")
                .into(itemView.image_view_cart)

            itemView.button_add.setOnClickListener {
               Toast.makeText(mContext, "add : ${dbHelper.getCurrentQuantity(product)}", Toast.LENGTH_SHORT).show()
                listener?.onButtonClicked(it, position)

            }
            itemView.button_remove.setOnClickListener {
                if(product.quantity!! > 1){
                    listener?.onButtonClicked(it, position)
                    Toast.makeText(mContext, "remove : ${dbHelper.getCurrentQuantity(product)}", Toast.LENGTH_SHORT).show()

                }
            }


        }

    }

    interface OnAdapterListener{
        fun onButtonClicked(view: View, position: Int)

    }

    fun setOnAdapterListener(onAdapterListener: OnAdapterListener){
        listener = onAdapterListener
    }

}



