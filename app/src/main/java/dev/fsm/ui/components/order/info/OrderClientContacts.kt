package dev.fsm.ui.components.order.info

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import dev.fsm.R
import dev.fsm.domain.models.global.client.Contact
import dev.fsm.ui.components.fields.global.Category
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.PaletteLightColors
import dev.fsm.ui.theme.spacing
import java.util.UUID

@Composable
fun OrderClientContacts(
    modifier: Modifier = Modifier,
    contacts: List<Contact>
) {
    val tag = remember { "PHONE" }
    Category(
        modifier = modifier,
        name = stringResource(id = R.string.client)
    ) {
        if (contacts.isEmpty()) {
            Text(
                text = stringResource(id = R.string.no_data),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)) {
            contacts.forEach { contact ->
                val phone = contact.phone ?: stringResource(id = R.string.no_data)
                val annotatedPhone = remember {
                    buildAnnotatedString {
                        append(phone)
                        addStringAnnotation(
                            tag = tag,
                            annotation = phone,
                            start = 1,
                            end = phone.length
                        )
                    }
                }
                if (contact.isMain) {
                    ContactItem(
                        contact = contact,
                        annotatedPhone = annotatedPhone
                    )
                } else {
                    Category(name = stringResource(id = R.string.additional_contact)) {
                        ContactItem(
                            contact = contact,
                            annotatedPhone = annotatedPhone
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ContactItem(
    context: Context = LocalContext.current,
    contact: Contact,
    annotatedPhone: AnnotatedString
) {
    Column {
        Text(
            text = contact.clientName ?: stringResource(id = R.string.no_data),
            style = MaterialTheme.typography.bodyMedium
        )
        MaterialTheme.spacing.SpacerExtraSmall()
        ClickableText(
            text = annotatedPhone,
            style = MaterialTheme.typography.bodyMedium.copy(
                textDecoration = TextDecoration.Underline,
                color = PaletteLightColors.Blue.Blue37
            ),
            onClick = {
                val uri = Uri.parse("tel:" + annotatedPhone.text)
                val intentToPhone = Intent(Intent.ACTION_DIAL, uri)
                try { context.startActivity(intentToPhone) } catch (_: Exception) { }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContactsFieldPreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            OrderClientContacts(
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                contacts = arrayListOf(
                    Contact(
                        id = UUID.randomUUID().toString(),
                        clientName = "Иванов Иван Иванович",
                        phone = "+7(909)509-59-69",
                        email = null,
                        isMain = true
                    ),
                    Contact(
                        id = UUID.randomUUID().toString(),
                        clientName = "Иванов Иван Иванович",
                        phone = "+7 905 400 60 06",
                        email = null,
                        isMain = false
                    ),
                    Contact(
                        id = UUID.randomUUID().toString(),
                        clientName = "Иванова Ивания Ивановна",
                        phone = "+79095999999",
                        email = null,
                        isMain = false
                    )
                )
            )
        }
    }
}