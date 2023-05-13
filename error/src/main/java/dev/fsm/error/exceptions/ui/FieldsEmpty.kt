package dev.fsm.error.exceptions.ui

open class FieldsEmpty : NoSuchFieldException() {
    override val message: String?
        get() = "All fields must be filled in."
}