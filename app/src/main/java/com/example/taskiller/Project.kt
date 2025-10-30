package com.example.taskiller

data class Project(
    val name: String,
    val taskCount: Int,
    val deadline: String,
    val userImages: List<Int> = emptyList() // IDs de les imatges dels usuaris
                  )