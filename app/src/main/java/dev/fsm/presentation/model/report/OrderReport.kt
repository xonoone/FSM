package dev.fsm.presentation.model.report

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderReport(
    val name         : String,
    val description  : String,
    val fields       : List<ReportView>
): Parcelable