package dev.fsm.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button as M3Button
import androidx.compose.material3.Checkbox as M3Checkbox
import androidx.compose.material3.FilterChip as M3FilterChip
import androidx.compose.material3.RadioButton as M3RadioButton

class Buttons {

    @Composable
    fun Button(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        shape: Shape = MaterialTheme.corners.mediumRoundCornerShape(),
        colors: ButtonColors = ButtonDefaults.buttonColors(),
        elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
        border: BorderStroke? = null,
        contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        content: @Composable RowScope.() -> Unit
    ) {
        M3Button(
            onClick = { onClick.invoke() },
            modifier = modifier,
            enabled = enabled,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            contentPadding = contentPadding,
            interactionSource = interactionSource,
            content = content
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FilterChip(
        selected: Boolean,
        onClick: () -> Unit,
        label: @Composable () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        leadingIcon: @Composable (() -> Unit)? = null,
        trailingIcon: @Composable (() -> Unit)? = null,
        shape: Shape = MaterialTheme.corners.mediumRoundCornerShape(),
        colors: SelectableChipColors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.background,
            labelColor = MaterialTheme.colorScheme.onBackground,
            iconColor = MaterialTheme.colorScheme.onBackground,
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
            selectedTrailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        elevation: SelectableChipElevation? = FilterChipDefaults.filterChipElevation(),
        border: SelectableChipBorder? = FilterChipDefaults.filterChipBorder(
            borderColor = MaterialTheme.colorScheme.outline,
        ),
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    ) = M3FilterChip(
        selected = selected,
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        label = label,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        elevation = elevation,
        colors = colors,
        shape = shape,
        border = border,
        interactionSource = interactionSource
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun <T> RowGroupChips(
        modifier: Modifier = Modifier,
        values: List<T>,
        selectedIndex: Int? = null,
        onSelectChanged: (Int) -> Unit = {},
        content: @Composable (T) -> Unit,
        spaceBetween: Dp = MaterialTheme.spacing.small
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spaceBetween)
        ) {
            values.forEachIndexed { index, item ->
                FilterChip(
                    modifier = Modifier.weight(1f),
                    selected = index == selectedIndex,
                    label = { content.invoke(item) },
                    onClick = { onSelectChanged.invoke(index) }
                )
            }
        }
    }

    @Composable
    fun Checkbox(
        modifier: Modifier = Modifier,
        checked: Boolean,
        onCheckedChange: ((Boolean) -> Unit)?,
        content: @Composable () -> Unit = {},
        enabled: Boolean = true,
        colors: CheckboxColors = CheckboxDefaults.colors(
            uncheckedColor = MaterialTheme.colorScheme.secondary,
            checkedColor = MaterialTheme.colorScheme.secondary
        ),
        shape: Shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = modifier
                .clip(shape = shape)
                .clickable { onCheckedChange?.invoke(!checked) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            M3Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = colors,
                enabled = enabled
            )
            content()
        }
    }

    @Composable
    fun RadioButton(
        modifier: Modifier = Modifier,
        selected: Boolean,
        onClick: () -> Unit,
        content: @Composable () -> Unit = {},
        enabled: Boolean = true,
        colors: RadioButtonColors = RadioButtonDefaults.colors(
            selectedColor = MaterialTheme.colorScheme.secondary,
            unselectedColor = MaterialTheme.colorScheme.secondary
        ),
    ) {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(24.dp))
                .clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            M3RadioButton(
                selected = selected,
                onClick = onClick,
                colors = colors,
                enabled = enabled
            )
            content()
        }
    }

    @Composable
    fun <T> RadioGroup(
        modifier: Modifier = Modifier,
        values: List<T>,
        selected: Int? = null,
        onChange: (index: Int) -> Unit,
        content: @Composable (T) -> Unit,
        spaceBetween: Dp = MaterialTheme.spacing.extraSmall
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(space = spaceBetween)
        ) {
            values.forEachIndexed { index, value ->
                MaterialTheme.buttons.RadioButton(
                    selected = index == selected,
                    onClick = { onChange.invoke(index) },
                    content = { content.invoke(value) }
                )
            }
        }
    }
}

val LocalButtons = compositionLocalOf { Buttons() }

val MaterialTheme.buttons: Buttons
    @Composable
    @ReadOnlyComposable
    get() = LocalButtons.current