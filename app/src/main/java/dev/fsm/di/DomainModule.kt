package dev.fsm.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.fsm.domain.repository.*
import dev.fsm.domain.usecase.cancel.CancelOrderForReasonById
import dev.fsm.domain.usecase.files.UploadFiles
import dev.fsm.domain.usecase.order.ChangeOrderStatusById
import dev.fsm.domain.usecase.order.FetchOrderInfoById
import dev.fsm.domain.usecase.orders.FetchActualOrdersByPage
import dev.fsm.domain.usecase.orders.FetchArchiveOrdersByPage
import dev.fsm.domain.usecase.orders.FetchNewOrdersByPage
import dev.fsm.domain.usecase.password.ChangePassword
import dev.fsm.domain.usecase.report.CompleteOrderWithReportById
import dev.fsm.domain.usecase.token.AuthenticationByLogin
import dev.fsm.domain.usecase.user.FetchUserInfoByPage

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideCancelOrderForReasonById(
        repository: ICancelOrderRepository
    ): CancelOrderForReasonById = CancelOrderForReasonById(
        repository = repository
    )

    @Provides
    fun provideUploadFiles(
        repository: IUploadFilesRepository
    ): UploadFiles = UploadFiles(
        repository = repository
    )


    @Provides
    fun provideChangeOrderStatusById(
        repository: IOrderRepository,
    ): ChangeOrderStatusById =
        ChangeOrderStatusById(
            repository = repository,
        )

    @Provides
    fun provideFetchOrderInfoById(
        repository: IOrderRepository,
    ): FetchOrderInfoById =
        FetchOrderInfoById(
            repository = repository,
        )

    @Provides
    fun provideFetchActualOrdersByPage(
        repository: IOrdersRepository
    ): FetchActualOrdersByPage = FetchActualOrdersByPage(
        repository = repository
    )

    @Provides
    fun provideFetchArchiveOrdersByPage(
        repository: IOrdersRepository
    ): FetchArchiveOrdersByPage = FetchArchiveOrdersByPage(
        repository = repository
    )

    @Provides
    fun provideFetchNewOrdersByPage(
        repository: IOrdersRepository
    ): FetchNewOrdersByPage = FetchNewOrdersByPage(
        repository = repository
    )

    @Provides
    fun provideChangePassword(
        repository: IChangePassRepository
    ): ChangePassword = ChangePassword(
        repository = repository
    )

    @Provides
    fun provideCompleteOrderWithReportById(
        repository: IOrderReportRepository
    ): CompleteOrderWithReportById = CompleteOrderWithReportById(
        repository = repository
    )

    @Provides
    fun provideAuthenticationByLogin(
        repository: IAuthenticationRepository,
    ): AuthenticationByLogin =
        AuthenticationByLogin(
            repository = repository,
        )

    @Provides
    fun provideFetchUserInfoByPage(
        repository: IUserRepository
    ): FetchUserInfoByPage = FetchUserInfoByPage(
        repository = repository
    )
}