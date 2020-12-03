package com.example.groceryapp.models

import java.io.Serializable

data class ProductResponse(
    val count: Int,
    val data: ArrayList<Product>,
    val error: Boolean
)

data class Product(
    var __v: Int? = null,
    var _id: String? = null,
    val catId: Int? = null,
    val created: String? = null,
    val description: String? = null,
    val image: String? = null,
    val mrp: Double? = null,
    val position: Int? = null,
    val price: Double? = null,
    val productName: String? = null,
    var quantity: Int? = 0,
    val status: Boolean? = null,
    val subId: Int? = null,
    val unit: String? = null
): Serializable {
    companion object{
        const val KEY_PRODUCT = "product"
    }
}