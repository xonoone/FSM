package dev.fsm.data.network.api.models.user

import com.google.gson.annotations.SerializedName
import dev.fsm.data.network.api.models.global.FileData
import dev.fsm.data.network.api.models.global.FileData.Companion.toFileData
import dev.fsm.data.network.api.models.global.Region
import dev.fsm.data.network.api.models.global.Region.Companion.toRegion
import dev.fsm.data.database.entities.User as DatabaseUser
import dev.fsm.domain.models.user.User as DomainUser

data class User(
    @SerializedName("id") val id: String? = null,
    @SerializedName("firstName") val firstName: String? = null,
    @SerializedName("lastName") val lastName: String? = null,
    @SerializedName("middleName") val middleName: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("mail") val email: String? = null,
    @SerializedName("rating") val points: Double? = null,
    @SerializedName("skills") val skills: ArrayList<String> = arrayListOf(),
    @SerializedName("regions") val regions: List<Region>? = listOf(),
    @SerializedName("photo") val photo: FileData? = null,
    @SerializedName("login") val login: String? = null
) {
    companion object {
        fun User.toUserDomain(): DomainUser = DomainUser(
            id = id ?: throw NullPointerException("No ID found"),
            firstName = firstName ?: "",
            lastName = lastName ?: "",
            middleName = middleName ?: "",
            phone = phone ?: "",
            email = email ?: "",
            photo = photo?.toFileData(),
            skills = skills,
            regions = regions?.map {
                it.toRegion()
            } ?: emptyList(),
            login = login ?: "",
            points = points ?: 0.0
        )

        fun User.toUserDatabase(): DatabaseUser = DatabaseUser(
            id = id ?: throw NullPointerException("No ID found"),
            firstName = firstName ?: "",
            lastName = lastName ?: "",
            middleName = middleName ?: "",
            phone = phone ?: "",
            email = email ?: "",
            skills = skills
        )
    }
}