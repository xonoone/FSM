package dev.fsm.data.network.api

import dev.fsm.data.network.api.annotations.UserID
import dev.fsm.data.network.api.models.ApiResponse
import dev.fsm.data.network.api.models.order.Order
import dev.fsm.data.network.api.models.order.ReasonForCancellation
import dev.fsm.data.network.api.models.user.User
import dev.fsm.data.network.api.models.user.authentication.Tokens
import dev.fsm.data.utils.config.NetworkConfig.API.ROUTES.AUTH.AUTH_BY_LOGIN
import dev.fsm.data.utils.config.NetworkConfig.API.ROUTES.AUTH.CHANGE_PASS
import dev.fsm.data.utils.config.NetworkConfig.API.ROUTES.AUTH.REFRESH_TOKEN
import dev.fsm.data.utils.config.NetworkConfig.API.ROUTES.CANCEL_ORDER_REASON
import dev.fsm.data.utils.config.NetworkConfig.API.ROUTES.ORDER.CANCEL
import dev.fsm.data.utils.config.NetworkConfig.API.ROUTES.ORDER.COMPLETE
import dev.fsm.data.utils.config.NetworkConfig.API.ROUTES.ORDER.GET_FILTERED
import dev.fsm.data.utils.config.NetworkConfig.API.ROUTES.ORDER.GET_ORDER
import dev.fsm.data.utils.config.NetworkConfig.API.ROUTES.ORDER.GET_ORDERS
import dev.fsm.data.network.api.annotations.Authenticated
import dev.fsm.data.network.api.models.global.FileData
import dev.fsm.data.utils.config.NetworkConfig.API.ROUTES.ORDER.STATUS.ACCEPT
import dev.fsm.data.utils.config.NetworkConfig.API.ROUTES.ORDER.STATUS.ACTIVE
import dev.fsm.data.utils.config.NetworkConfig.API.ROUTES.ORDER.STATUS.INROAD
import dev.fsm.data.utils.config.NetworkConfig.API.ROUTES.ORDER.STATUS.REJECT
import dev.fsm.data.utils.config.NetworkConfig.API.ROUTES.UPLOAD_FILES
import dev.fsm.data.utils.config.NetworkConfig.API.ROUTES.USER.USER_INFO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    /**
     * User Authorization Request
     *
     * @param login is the user login
     * @param password is the user password
     *
     * @return Return the http response with the requested data and Api code
     */
    @POST(AUTH_BY_LOGIN)
    suspend fun authorization(
        @Query("login") login: String?,
        @Query("password") password: String?
    ): Response<ApiResponse<Tokens>>

    /**
     * Password Change Request
     *
     * @param newPassword is new user password entered
     *
     * @return Return the http response with the requested data and Api code
     */
    @POST(CHANGE_PASS)
    @Authenticated
    @UserID
    suspend fun changePass(
        @Query("newpassword") newPassword: String
    ): Response<ApiResponse<Any?>>

    /**
     * Refresh Token Request
     *
     * @param refreshToken is token from the local database
     *
     * @return Return the http response with the requested data and Api code
     */
    @POST(REFRESH_TOKEN)
    suspend fun refreshToken(
        @Query("refreshtoken") refreshToken: String
    ): Response<ApiResponse<Tokens>>

    /**
     * Request to receive order by id
     *
     * @param id is order Id.
     *
     * @return Return the http response with the requested data and Api code
     */
    @GET(GET_ORDER)
    @Authenticated
    suspend fun getOrder(@Query("id") id: String): Response<ApiResponse<Order>>

    /**
     * Request to receive orders with filter params
     *
     *  @param dateFrom starting from what date the orders will arrive
     *  @param dateTo ending by what date the orders will arrive
     *  @param isEarlyFirst is responsible for sorting. If true, the orders will come sorted from the nearest. If false, then from the last date
     *  @param statuses array of orders statuses. Must be sent in the request body as a json array
     *
     *  @return Return the http response with the requested data and Api code
     */
    @POST(GET_FILTERED)
    @Authenticated
    @UserID
    suspend fun getOrders(
        @Query("dateFrom") dateFrom: String?,
        @Query("dateTo") dateTo: String?,
        @Query("isEarlyFirst") isEarlyFirst: Boolean?,
        @Body statuses: RequestBody
    ): Response<ApiResponse<ArrayList<Order>>>

    /**
     * Request to receive orders
     *
     * @param statuses array of orders statuses. Must be sent in the request body as a json array
     *
     * @return Return the http response with the requested data and Api code
     */
    @POST(GET_ORDERS)
    @Authenticated
    @UserID
    suspend fun getOrders(@Body statuses: RequestBody): Response<ApiResponse<ArrayList<Order>>>

    /**
    * Sends a request to the ACCEPT endpoint to set the status of the order with the specified ID to 'Accepted'.
    * @param orderId The ID of the order to be updated.
    * @return [ApiResponse] The API response returned by the server.
    */
    @POST(ACCEPT)
    @Authenticated
    suspend fun setStatusAccept(
        @Query("orderId") orderId: String
    ): Response<ApiResponse<Any?>>

    /**
     * Sends a request to the INROAD endpoint to set the status of the order with the specified ID to 'InRoad'.
     * @param orderId The ID of the order to be updated.
     * @return [ApiResponse] The API response returned by the server.
     */
    @POST(INROAD)
    @Authenticated
    suspend fun setStatusInRoad(
        @Query("orderId") orderId: String
    ): Response<ApiResponse<Any?>>

    /**
     * Sends a request to the ACTIVE endpoint to set the status of the order with the specified ID to 'Active'.
     * @param orderId The ID of the order to be updated.
     * @return [ApiResponse] The API response returned by the server.
     */
    @POST(ACTIVE)
    @Authenticated
    suspend fun setStatusActive(
        @Query("orderId") orderId: String
    ): Response<ApiResponse<Any?>>

    /**
     * Sends a request to the REJECT endpoint to set the status of the order with the specified ID to 'Rejected'.
     * @param orderId The ID of the order to be updated.
     * @return [ApiResponse] The API response returned by the server.
     */
    @POST(REJECT)
    @Authenticated
    suspend fun setStatusReject(
        @Query("orderId") orderId: String
    ): Response<ApiResponse<Any?>>

    /**
     * Request to cancel order
     *
     * @param orderId is order Id
     * @param reasonId is reason Id
     * @param note is description
     *
     * @return Return the http response with the requested data and Api code
     */
    @POST(CANCEL)
    @Authenticated
    suspend fun cancelOrder(
        @Query("orderId") orderId: String,
        @Query("reasonId") reasonId: String,
        @Query("note") note: String,
    ): Response<ApiResponse<Any?>>

    /**
     * Request to complete order
     *
     * @param id is order Id
     *
     * @return Return the http response with the requested data and Api code
     */
    @PUT(COMPLETE)
    @Authenticated
    suspend fun completeOrder(
        @Query("id") id: String,
        @Body fieldsJson: RequestBody
    ): Response<ApiResponse<Any?>>

    /**
     * Request to get user
     *
     * @return Return the http response with the requested data and Api code
     */
    @GET(USER_INFO)
    @Authenticated
    @UserID
    suspend fun getUser(): Response<ApiResponse<User>>

    /**
     * Request to get reasons for cancellation
     *
     * @return Return the http response with the requested data and Api code
     */
    @GET(CANCEL_ORDER_REASON)
    @Authenticated
    suspend fun getReasonsForCancellation(): Response<ApiResponse<ArrayList<ReasonForCancellation>>>

    /**
     * Uploads multiple files to the server using a multipart request.
     *
     * @param files A list of [MultipartBody.Part] objects representing the files to be uploaded.
     * @return A [Response] object containing an [ApiResponse] object with a list of [FileData] objects.
     */
    @Multipart
    @POST(UPLOAD_FILES)
    @Authenticated
    suspend fun uploadFiles(
        @Part files: List<MultipartBody.Part>
    ): Response<ApiResponse<List<FileData>>>
}