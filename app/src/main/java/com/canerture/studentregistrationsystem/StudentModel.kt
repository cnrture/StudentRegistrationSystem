package com.canerture.studentregistrationsystem

import java.io.Serializable

data class StudentModel(
    val id: Int,
    val studentId: String,
    val gender: String,
    val name: String,
    val surname: String,
    val faculty: String,
    val department: String,
    val lecturer: String
) : Serializable
