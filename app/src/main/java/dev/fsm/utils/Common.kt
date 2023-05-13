package dev.fsm.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import dev.fsm.domain.utils.OrderFiledTypes
import dev.fsm.presentation.model.report.IField
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object Common {

    fun Uri.getName(context: Context): String {
        var fileName = ""
        val returnCursor = context.contentResolver.query(this, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            fileName = nameIndex.let { returnCursor.getString(it) }
            returnCursor.close()
        }
        return fileName
    }

    fun Uri.toTempFile(context: Context): File? {
        var file: File? = null
        context.contentResolver.openFileDescriptor(
            this, "r", null
        )?.let { parcelFileDescriptor ->
            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            file = File(context.cacheDir, this.getName(context))
            val outputStream = FileOutputStream(file)
            IOUtils.copy(inputStream, outputStream)
            file?.deleteOnExit()
            parcelFileDescriptor.close()
        }
        return file
    }

    fun IField.toType(): String = when (this) {
        is IField.Photo -> OrderFiledTypes.Photo
        is IField.File -> OrderFiledTypes.File
        is IField.List -> OrderFiledTypes.List
        is IField.Date -> OrderFiledTypes.Date
        is IField.DateTime -> OrderFiledTypes.DateTime
        is IField.Text -> OrderFiledTypes.Text
        is IField.Money -> OrderFiledTypes.Money
        is IField.Long -> OrderFiledTypes.Long
        is IField.Double -> OrderFiledTypes.Double
        is IField.Link -> OrderFiledTypes.Link
        is IField.Condition -> OrderFiledTypes.Condition
        else -> ""
    }
}