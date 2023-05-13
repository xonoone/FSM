package dev.fsm.ui.components.order.info

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.fsm.R
import dev.fsm.ui.components.fields.global.Category
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.spacing

@Composable
fun OrderAddress(
    modifier: Modifier = Modifier,
    address: String?
) {
    val context = LocalContext.current
    val copyLabel = stringResource(id = R.string.copy_address)
    Category(
        modifier = modifier,
        name = stringResource(id = R.string.address)
    ) {
        address?.let {
            Text(
                text = address,
                style = MaterialTheme.typography.bodyMedium
            )
            MaterialTheme.spacing.SpacerExtraSmall()
            TextButton(
                modifier = Modifier.height(24.dp),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp),
                onClick = {
                    address.copyToClip(context = context, label = copyLabel)
                },
            ) {
                Text(
                    text = stringResource(id = R.string.copy_address),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        } ?: Text(
            text = stringResource(id = R.string.no_data),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

private fun String.copyToClip(context: Context, label: String) {
    val clip = ClipData.newPlainText(label, this)
    val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(clip)

    Toast.makeText(context,context.getString(R.string.copied), Toast.LENGTH_LONG).show()
}

@Preview(showBackground = true)
@Composable
fun OrderAddressPreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            OrderAddress(
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                address = "г Самара, ул Ленинская, д 23, кв 26"
            )
        }
    }
}