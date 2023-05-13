package dev.fsm.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.fsm.data.database.entities.User
import dev.fsm.data.utils.config.DatabaseConfig.USER

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: User)

    @Query("SELECT * FROM $USER")
    fun get(): User?

    @Query("DELETE FROM $USER")
    fun clear()
}