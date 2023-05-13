package dev.fsm.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.fsm.R

val Roboto = FontFamily(
    Font(R.font.roboto_black, FontWeight.Black),
    Font(R.font.roboto_black_italic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.roboto_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_thin, FontWeight.Thin),
    Font(R.font.roboto_thin_italic, FontWeight.Thin, FontStyle.Italic)
)

val AppTypography = Typography(
    labelSmall = TextStyle(
        fontFamily = Roboto,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.W600,
        fontSize = 8.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Roboto,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.W600,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Roboto,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Roboto,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    bodyMedium = TextStyle(
        /* Regular style for content */
        fontFamily = Roboto,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Roboto,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Roboto,
        fontStyle = FontStyle.Normal,
        fontWeight= FontWeight.Normal,
        fontSize = 12.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Roboto,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Roboto,
        fontStyle = FontStyle.Normal,
        fontWeight= FontWeight.W500,
        fontSize = 24.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Roboto,
        fontStyle = FontStyle.Normal,
        fontWeight= FontWeight.W800,
        fontSize = 28.sp
    ),
    displayMedium = TextStyle(
        /* For medium App logo */
        fontFamily = Roboto,
        fontStyle = FontStyle.Italic,
        fontWeight= FontWeight.W800,
        fontSize = 64.sp
    ),
    displayLarge = TextStyle(
        /* For large App logo */
        fontFamily = Roboto,
        fontStyle = FontStyle.Italic,
        fontWeight= FontWeight.W800,
        fontSize = 96.sp
    )
)