package com.example.groceryapp.models

data class UserResponse(
    val token: String,
    val user: User
)

data class User(
    val _id: String,
    val email: String,
    val firstName: String,
    val mobile: String,
    val password: String
)