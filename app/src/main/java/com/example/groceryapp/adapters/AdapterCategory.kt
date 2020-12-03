package com.example.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.App.Config
import com.example.groceryapp.R
import com.example.groceryapp.SubCategoryActivity
import com.example.groceryapp.models.Category
import com.example.groceryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_grocery_adapter.view.*


class AdapterCategory(var mContext: Context): RecyclerView.Adapter<AdapterCategory.ViewHolder>(){
    var mList: ArrayList<Category> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_grocery_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var category = mList[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(data: ArrayList<Category>){
        mList = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(category: Category){
            itemView.text_view_title.text = category.catName

            Picasso
                .get()
                .load("${Config.IMAGE_URL + category.catImage}")
                .into(itemView.image_view_row)

            itemView.setOnClickListener {
                var intent = Intent(mContext,SubCategoryActivity::class.java)
                intent.putExtra(Category.KEY_CATEGORY, category)
                mContext.startActivity(intent)
            }

        }

    }
}
