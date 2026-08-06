package com.example.data.model

data class UserAccount(
    val id: String = "USR_9082",
    val fullName: String = "Chidimma Okonkwo",
    val email: String = "c.okonkwo@pharmacy.edu.ng",
    val phone: String = "+234 803 123 4567",
    val institution: String = "School of Health Technology, Yaba",
    val state: String = "Lagos State",
    val indexNumber: String = "PHT/2024/0892",
    val isPremium: Boolean = false,
    val activationDate: String? = null
)
