package dev.fsm.ui.components.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.fsm.R
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.spacing

@Composable
fun AddButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes name: Int = R.string.button_add_name,
    enabled: Boolean = true
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.secondary
        ),
        enabled = enabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_add_circle_outline),
                contentDescription = stringResource(id = name),
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))
            Text(
                text = stringResource(id = name),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddButtonPreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.spacing.medium)
            ) {
                AddButton(
                    name = R.string.add_doc,
                    onClick = {

                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                AddButton(
                    name = R.string.add_photo,
                    onClick = {

                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                AddButton(
                    onClick = {

                    }
                )
            }
        }
    }
}