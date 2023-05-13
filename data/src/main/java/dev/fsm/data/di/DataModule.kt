package dev.fsm.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.fsm.data.repository.*
import dev.fsm.data.storage.retrofit.*
import dev.fsm.data.storage.room.*
import dev.fsm.domain.repository.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DataModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        retrofit: TokenStorageRetrofit,
        room: TokenStorageRoom
    ): IAuthenticationRepository =
        AuthenticationRepositoryImpl(network = retrofit, database = room)

    @Provides
    @Singleton
    fun provideCancelOrderRepository(
        retrofit: CancelOrderStorageRetrofit
    ): ICancelOrderRepository =
        CancelOrderRepositoryImpl(network = retrofit)

    @Provides
    @Singleton
    fun provideChangePassRepository(
        retrofit: ChangePassStorageRetrofit
    ): IChangePassRepository =
        ChangePassRepositoryImpl(network = retrofit)

    @Provides
    @Singleton
    fun provideOrderReportRepository(
        retrofit: OrderReportStorageRetrofit,
        room: OrderReportStorageRoom
    ): IOrderReportRepository =
        OrderReportRepositoryImpl(network = retrofit, database = room)

    @Provides
    @Singleton
    fun provideOrderRepository(
        retrofit: OrderStorageRetrofit,
        room: OrderStorageRoom
    ): IOrderRepository = OrderRepositoryImpl(network = retrofit, database = room)

    @Provides
    @Singleton
    fun provideOrdersRepository(
        retrofit: OrdersStorageRetrofit,
        room: OrdersStorageRoom
    ): IOrdersRepository = OrdersRepositoryImpl(network = retrofit, database = room)

    @Provides
    @Singleton
    fun provideUploadFilesRepository(
        retrofit: UploadFilesStorageRetrofit
    ): IUploadFilesRepository =
        UploadFilesRepositoryImpl(network = retrofit)

    @Provides
    @Singleton
    fun provideUserRepository(
        userStorageRetrofit: UserStorageRetrofit,
        userStorageRoom: UserStorageRoom
    ): IUserRepository = UserRepositoryImpl(network = userStorageRetrofit, database = userStorageRoom)
}