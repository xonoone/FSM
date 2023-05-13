package dev.fsm.error.exceptions.ui

import java.util.concurrent.TimeoutException

open class Timeout : TimeoutException() {
    override val message: String?
        get() = "Check your internet connection and repeat the request."
}