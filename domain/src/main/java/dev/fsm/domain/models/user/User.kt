package dev.fsm.domain.models.user

import dev.fsm.domain.models.files.FileData
import dev.fsm.domain.models.global.Region

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val phone: String,
    val email: String,
    val login: String,
    val photo: FileData? = null,
    val skills: List<String>? = emptyList(),
    val regions: List<Region>? = emptyList(),
    val points: Double
)