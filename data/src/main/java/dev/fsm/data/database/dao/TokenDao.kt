package dev.fsm.data.database.dao

import androidx.room.*
import dev.fsm.data.database.entities.Tokens
import dev.fsm.data.utils.config.DatabaseConfig.TOKEN

@Dao
interface TokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(tokens: Tokens)

    @Query("SELECT * FROM $TOKEN")
    suspend fun get(): Tokens?

    @Query("DELETE FROM $TOKEN")
    suspend fun clear()

}