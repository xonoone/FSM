package dev.fsm.error.utils

object Codes {

    const val UNKNOWN_ERROR = -1

    object Api {
        const val OK = 0
        object ACCESS {
            const val INVALID_TOKEN = 100
            const val USER_IS_BLOCKED = 101
            const val TOKEN_EXPIRED = 102
        }

        object USER {
            const val USER_NOT_FOUND = 201
            const val INCORRECT_PASSWORD = 202
        }
    }
    object Http {
        object OK {
            const val start = 200
            const val end = 299
        }
        object ETERNAL {
            const val start = 500
            const val end = 599
            const val NOT_ENOUGH_MEMORY = 507
        }
        object ACCESS {
            const val UNAUTHORIZED = 401
            const val TOKEN_EXPIRED = 401
            const val USER_IS_BLOCKED = 401
            const val ACCESS_FORBIDDEN = 403
        }
    }
}