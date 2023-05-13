package dev.fsm.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.fsm.data.utils.config.DatabaseConfig.TOKEN
import dev.fsm.data.network.api.models.user.authentication.Tokens as DataTokens

@Entity(tableName = TOKEN)
data class Tokens(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "user_id")       val userId:         String,
    @ColumnInfo(name = "access_token")  val accessToken:    String,
    @ColumnInfo(name = "refresh_token") val refreshToken:   String
) {
    companion object {
        fun map(params: DataTokens): Tokens = Tokens(
            userId = params.userId ?: "",
            accessToken = params.accessToken ?: "",
            refreshToken = params.refreshToken ?: ""
        )
    }
}
