package com.example.groceryapp.models

import java.io.Serializable

data class AddressResponse(
    val count: Int,
    val data: ArrayList<Address>,
    val error: Boolean
)

data class Address(

    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String,
    val userId: String,
    val __v: Int? = null,
    val _id: String? = null
) : Serializable {
    companion object {
        const val KEY_ADDRESS = "address"
    }

}