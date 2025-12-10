package com.euphoria.selfcare.euphoria

data class Journal(
    val id: String? = "",
    val uid: String? = "",
    val date: String? = "",
    val affirmation: String? = "",
    val reflection: String? = "",
    val timestamp: Long? = 0
)
