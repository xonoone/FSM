package dev.fsm.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.fsm.data.database.dao.*
import dev.fsm.data.database.entities.Tokens
import dev.fsm.data.database.entities.User
import dev.fsm.data.utils.Converter

@Database(entities = [Tokens::class, User::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun ordersDao(): OrdersDao
    abstract fun orderDao(): OrderDao
    abstract fun orderReportDao(): OrderReportDao
    abstract fun tokenDao(): TokenDao
    abstract fun userDao(): UserDao
}