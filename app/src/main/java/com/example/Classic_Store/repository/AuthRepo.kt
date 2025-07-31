package com.example.Classic_Store.repository

interface AuthRepo {
    fun login(email:String,password:String,callback:(Boolean,String)->Unit)
}