package com.example.groceryapp.adapters


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import  android.view.View
import android.view.ViewGroup
import com.example.groceryapp.App.Config
import com.example.groceryapp.R
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.models.Address
import kotlinx.android.synthetic.main.row_address_adapter.view.*



class AdapterAddress(var mContext: Context) : RecyclerView.Adapter<AdapterAddress.ViewHolder>() {
    var mList: ArrayList<Address> = ArrayList()
    var dbHelper = DBHelper(mContext)
    var listener: OnAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAddress.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_address_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var address = mList[position]
        holder.bind(address, position)
    }


    fun setData(data: ArrayList<Address>) {
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

        fun bind(address: Address, position: Int) {

            itemView.text_view_street.text  = address.streetName
            itemView.text_view_zipcode.text = address.pincode.toString()
            itemView.textview_housenumber.text = address.houseNo
            itemView.text_view_city.text = address.city

        }

    }

    interface OnAdapterListener{
        fun onButtonClicked(view: View, position: Int)

    }

    fun setOnAdapterListener(onAdapterListener: OnAdapterListener){
        listener = onAdapterListener
    }

}



