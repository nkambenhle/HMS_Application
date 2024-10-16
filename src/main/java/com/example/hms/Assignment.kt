package com.example.hms.model

data class Assignment(
    val _id: String,
    val Title: String,
    val Description: String,
    val Marks: Int,
    val Open_Date: String,
    val Due_Date: String,
    val Module_ID: Module
)

data class Module(
    val _id: String,
    val Module_Name: String
)
