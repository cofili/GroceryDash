package com.example.groceryapp.models

data class OrderResponse(
    val __v: Int,
    val _id: String,
    val date: String,
    val orderStatus: String,
    val orderSummary: OrderSummary,
    val products: List<Product>,
    val shippingAddress: ShippingAddress,
    val user: User,
    val userId: String
)

data class OrderSummary(
    val _id: String,
    val deliveryCharges: Int,
    val discount: Double,
    val orderAmount: Int,
    val ourPrice: Double,
    val totalAmount: Int
)

//data class Product(
//    val _id: String,
//    val image: String,
//    val mrp: Int,
//    val price: Int,
//    val productName: String,
//    val quantity: Int
//)

data class ShippingAddress(
    val _id: String,
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val type: String
)

//data class User(
//    val _id: String,
//    val email: String,
//    val mobile: String
//)