package dev.fsm.presentation.screens.user.info.models

import dev.fsm.domain.models.user.User as DomainUser
import dev.fsm.presentation.model.file.FileData
import dev.fsm.presentation.model.file.FileData.Companion.toFileData
import dev.fsm.presentation.screens.user.info.models.Region.Companion.toRegion

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
    val rating: Double
) {
    companion object {
        fun DomainUser.toUser(): User = User(
            id = id,
            firstName = firstName,
            lastName = lastName,
            phone = phone,
            middleName = middleName,
            email = email,
            login = login,
            photo = photo?.toFileData(),
            skills = skills,
            regions = regions?.map { it.toRegion() },
            rating = points
        )
    }
}