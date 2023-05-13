package dev.fsm.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.fsm.data.database.AppDatabase
import dev.fsm.data.database.dao.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class RoomModule {

    @Provides
    fun provideOrdersDao(
        database: AppDatabase
    ): OrdersDao = database.ordersDao()

    @Provides
    fun provideOrderDao(
        database: AppDatabase
    ): OrderDao = database.orderDao()

    @Provides
    fun provideOrderReportDao(
        database: AppDatabase
    ): OrderReportDao = database.orderReportDao()

    @Provides
    fun provideTokenDao(
        database: AppDatabase
    ): TokenDao = database.tokenDao()

    @Provides
    fun provideUserDao(
        database: AppDatabase
    ): UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "Reader"
        ).build()
    }
}