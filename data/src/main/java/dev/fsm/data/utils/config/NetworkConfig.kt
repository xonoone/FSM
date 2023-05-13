package dev.fsm.data.utils.config

object NetworkConfig {
    object API {
        const val BASE_URL = "http://62.113.105.29:5454/api/"
        const val ACCESS_TOKEN_HEADER_TYPE = "Authorization"
        const val ACCESS_TOKEN_PREFIX = "Bearer "
        const val QUERY_USER_ID = "id"

        object ROUTES {
            object AUTH {
                const val AUTH_BY_LOGIN = "auth/employee"
                const val CHANGE_PASS = "auth/employee/changepass"
                const val REFRESH_TOKEN = "auth/employee/refresh_token"
            }
            object USER {
                const val USER_INFO = "employee"
            }
            object ORDER {
                object STATUS {
                    const val ACCEPT = "order/accept"
                    const val INROAD = "order/inroad"
                    const val ACTIVE = "order/active"
                    const val REJECT = "order/reject"
                }
                const val SET_STATUS = "order/setStatus"
                const val CANCEL = "order/cancel"
                const val COMPLETE = "order/complete"
                const val GET_ORDERS = "order/executor"
                const val GET_FILTERED = "order/filtered/executor"
                const val GET_ORDER = "order"
            }
            const val CANCEL_ORDER_REASON = "CancellationReason"
            const val UPLOAD_FILES = "Upload/UploadFiles"
        }
    }
}