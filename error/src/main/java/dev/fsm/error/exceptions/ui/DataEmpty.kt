package dev.fsm.error.exceptions.ui

open class DataEmpty : NullPointerException() {
    override val message: String?
        get() = "The data is empty."
}