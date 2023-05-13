package dev.fsm.domain.utils

/**
 * This interface provides methods to check nullability of an object and perform logical operations on it.
 */
interface INullabilityChecker {

    /**
    * Checks whether the given object is null.
    * @return true if the object is null, false otherwise.
    */
    fun Any?.isNull(): Boolean

    /**
    * Checks whether the given object is not null.
    * @return true if the object is not null, false otherwise.
    */
    fun Any?.notNull(): Boolean

    /**
    * Performs a logical OR operation on the given object and another object, both of which may be null.
    * @param value The object to perform the logical OR operation with.
    * @return true if either object is not null, false otherwise.
    */
    fun Any?.orNotNull(value: Any?): Boolean

    /**
    * Performs a logical AND operation on the given object and another object, both of which may be null.
    * @param value The object to perform the logical AND operation with.
    * @return true if both objects are not null, false otherwise.
    */
    fun Any?.andNotNull(value: Any?): Boolean


    /**
    * An object that implements the INullCheck interface.
    * Provides the implementation of the methods defined in the interface.
    */
    object NullabilityChecker : INullabilityChecker {

        /**
        * @see NullabilityChecker.isNull
        */
        override fun Any?.isNull(): Boolean = this == null

        /**
         * @see NullabilityChecker.notNull
         */
        override fun Any?.notNull(): Boolean = this != null

        /**
         * @see NullabilityChecker.orNotNull
         */
        override infix fun Any?.orNotNull(value: Any?): Boolean = (this != null) or (value != null)

        /**
         * @see NullabilityChecker.andNotNull
         */
        override infix fun Any?.andNotNull(value: Any?): Boolean =
            (this != null) and (value != null)
    }
}