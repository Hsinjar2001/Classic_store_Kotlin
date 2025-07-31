package com.example.Classic_Store.repository

interface SignUpTestRepo {
    fun signup(email:String,password:String,callback: (Boolean, String, String) -> Unit)

}