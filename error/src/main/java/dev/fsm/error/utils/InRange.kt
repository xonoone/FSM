package dev.fsm.error.utils

/**
 * Annotation used to mark a parameter as having a range constraint.
 * The parameter's value must be within the specified range (inclusive).
 * @param start The start of the allowed range (inclusive).
 * @param end The end of the allowed range (inclusive).
 * @throws IllegalArgumentException if the parameter's value is not within the specified range.
**/
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class InRange(val start: Int, val end: Int)