package dev.fsm.ui.theme

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextField as M3OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import dev.fsm.R
import dev.fsm.ui.theme.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class Fields {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun OutlinedTextField(
        value: TextFieldValue,
        onValueChange: (TextFieldValue) -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        readOnly: Boolean = false,
        textStyle: TextStyle = LocalTextStyle.current.copy(
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        label: @Composable (() -> Unit)? = null,
        placeholder: @Composable (() -> Unit)? = null,
        leadingIcon: @Composable (() -> Unit)? = null,
        trailingIcon: @Composable (() -> Unit)? = null,
        supportingText: @Composable (() -> Unit)? = null,
        isError: Boolean = false,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        keyboardActions: KeyboardActions = KeyboardActions.Default,
        singleLine: Boolean = false,
        maxLines: Int = Int.MAX_VALUE,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        shape: Shape = MaterialTheme.corners.mediumRoundCornerShape(),
        colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.outline,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            errorBorderColor = MaterialTheme.colorScheme.error,
            focusedLabelColor = MaterialTheme.colorScheme.outline,
            unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
            errorLabelColor = MaterialTheme.colorScheme.error,
            cursorColor = MaterialTheme.colorScheme.outline,
            errorCursorColor = MaterialTheme.colorScheme.error,
            selectionColors = TextSelectionColors(
                handleColor = if (isError) MaterialTheme.colorScheme.error
                else LocalTextSelectionColors.current.handleColor,
                backgroundColor = if (isError) MaterialTheme.colorScheme.error.copy(alpha = 0.2F)
                else LocalTextSelectionColors.current.backgroundColor
            ),
            errorSupportingTextColor = MaterialTheme.colorScheme.error,
            focusedTrailingIconColor = MaterialTheme.colorScheme.tertiary,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        M3OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            label = label,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            supportingText = supportingText,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors
        )
    }

    @Composable
    fun ClickableField(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit,
        shape: Shape = MaterialTheme.corners.mediumRoundCornerShape(),
        align: Alignment = Alignment.CenterStart,
        color: Color = MaterialTheme.colorScheme.outline
    ) {
        val minWidth = 144.dp
        val minHeight = 56.dp
        Box(
            modifier = modifier
                .border(width = 1.dp, color = color, shape = shape)
                .clip(shape)
                .defaultMinSize(minWidth = minWidth, minHeight = minHeight)
                .clickable(onClick = onClick)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            contentAlignment = align,
            content = { content() }
        )
    }

    @Composable
    fun ClickableDateField(
        modifier: Modifier = Modifier,
        value: LocalDate? = null,
        onValueChange: (LocalDate) -> Unit,
        @StringRes name: Int? = null
    ) {
        val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
        val description: String = stringResource(name ?: R.string.date)

        val formattedValue = value?.format(dateFormatter) ?: description

        var dateFieldValueState by remember { mutableStateOf(TextFieldValue(text = formattedValue)) }
        val dateFieldValue = dateFieldValueState.copy(formattedValue)
        var lastDateValue by remember(formattedValue) { mutableStateOf(formattedValue) }

        val dialogState = rememberMaterialDialogState()

        ClickableField(
            modifier = modifier,
            onClick = {
                if (!dialogState.showing) dialogState.show()
            },
            content = {
                Text(
                    text = dateFieldValue.text,
                    style = MaterialTheme.typography.bodyMedium
                )
                MaterialDialog(
                    dialogState = dialogState,
                    buttons = {
                        positiveButton(
                            text = stringResource(id = android.R.string.ok),
                            textStyle = TextStyle(color = MaterialTheme.colorScheme.tertiary)
                        )
                        negativeButton(
                            text = stringResource(id = android.R.string.cancel),
                            textStyle = TextStyle(color = MaterialTheme.colorScheme.tertiary)
                        )
                    }
                ) {
                    datepicker(
                        title = stringResource(id = R.string.pick_date),
                        colors = DatePickerDefaults.colors(
                            headerBackgroundColor = MaterialTheme.colorScheme.primary,
                            dateActiveBackgroundColor = MaterialTheme.colorScheme.primary
                        )
                    ) { newDateValueState ->
                        val formattedNewDateValueState = TextFieldValue(
                            text = newDateValueState.format(dateFormatter) ?: description
                        )

                        dateFieldValueState = formattedNewDateValueState

                        val dateChangedSinceLastInvocation =
                            lastDateValue != formattedNewDateValueState.text
                        lastDateValue = formattedNewDateValueState.text

                        if (dateChangedSinceLastInvocation) {
                            onValueChange(newDateValueState)
                        }
                    }
                }
            }
        )
    }

    @Composable
    fun ClickableTimeField(
        modifier: Modifier = Modifier,
        value: LocalTime? = null,
        onValueChange: (LocalTime) -> Unit,
        @StringRes name: Int? = null
    ) {
        val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        val description: String = stringResource(name ?: R.string.time)
        val formattedValue = value?.format(timeFormatter) ?: description

        var timeFieldValueState by remember { mutableStateOf(TextFieldValue(text = formattedValue)) }
        val timeFieldValue = timeFieldValueState.copy(formattedValue)
        var lastTimeValue by remember(formattedValue) { mutableStateOf(formattedValue) }

        val dialogState = rememberMaterialDialogState()

        ClickableField(
            modifier = modifier,
            onClick = {
                if (!dialogState.showing) dialogState.show()
            },
            content = {
                Text(
                    text = timeFieldValue.text,
                    style = MaterialTheme.typography.bodyMedium
                )
                MaterialDialog(
                    dialogState = dialogState,
                    buttons = {
                        positiveButton(
                            text = stringResource(id = android.R.string.ok),
                            textStyle = TextStyle(color = MaterialTheme.colorScheme.tertiary)
                        )
                        negativeButton(
                            text = stringResource(id = android.R.string.cancel),
                            textStyle = TextStyle(color = MaterialTheme.colorScheme.tertiary)
                        )
                    }
                ) {
                    timepicker(
                        title = stringResource(id = R.string.pick_time),
                        is24HourClock = true,
                        colors = TimePickerDefaults.colors(
                            inactiveBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
                            activeBackgroundColor = MaterialTheme.colorScheme.primary,
                            inactivePeriodBackground = MaterialTheme.colorScheme.primaryContainer,
                            selectorColor = MaterialTheme.colorScheme.primary
                        )
                    ) { newTimeValueState ->
                        val formattedNewDateValueState = TextFieldValue(
                            text = newTimeValueState.format(timeFormatter) ?: description
                        )
                        timeFieldValueState = formattedNewDateValueState

                        val dateChangedSinceLastInvocation =
                            lastTimeValue != formattedNewDateValueState.text
                        lastTimeValue = formattedNewDateValueState.text

                        if (dateChangedSinceLastInvocation) {
                            onValueChange(newTimeValueState)
                        }
                    }
                }
            }
        )
    }
}

val LocalFields = compositionLocalOf { Fields() }

val MaterialTheme.fields: Fields
    @Composable
    @ReadOnlyComposable
    get() = LocalFields.current


@Preview(showBackground = true)
@Composable
private fun ClickableDialogFieldPreview() {
    AppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.medium)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var date: LocalDate? by rememberSaveable { mutableStateOf(null) }
                    MaterialTheme.fields.ClickableDateField(
                        value = date,
                        onValueChange = { picked ->
                            date = picked
                        }
                    )
                    Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))
                    MaterialTheme.buttons.Button(
                        onClick = { date = null }
                    ) {
                        Text(text = "Clear")
                    }
                    Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))
                    CircularProgressIndicator()
                }
                Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var time: LocalTime? by rememberSaveable { mutableStateOf(null) }
                    MaterialTheme.fields.ClickableTimeField(
                        value = time,
                        onValueChange = { picked ->
                            time = picked
                        }
                    )
                    Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))
                    MaterialTheme.buttons.Button(
                        onClick = { time = null }
                    ) {
                        Text(text = "Clear")
                    }
                    Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))
                    CircularProgressIndicator()
                }
            }
        }
    }
}