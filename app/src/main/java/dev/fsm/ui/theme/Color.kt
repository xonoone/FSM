package dev.fsm.ui.theme

import androidx.compose.ui.graphics.Color
import dev.fsm.domain.utils.OrderStatuses
import dev.fsm.ui.theme.PaletteLightColors.Blue
import dev.fsm.ui.theme.PaletteLightColors.Gray
import dev.fsm.ui.theme.PaletteLightColors.Black

// Theme
val Primary = Blue.Blue64               // Blue Primary
val OnPrimary = Blue.Blue100            // White
val PrimaryContainer = Blue.Blue97      // Blue Background
val OnPrimaryContainer = Blue.Blue0     // Black

val Secondary = Blue.Blue37             // Blue Dark
val OnSecondary = Blue.Blue100          // White
val SecondaryContainer = Blue.Blue91    // Blue Light
val OnSecondaryContainer = Blue.Blue0   // Black

val Tertiary = Gray.gray58              // Gray Dark
val OnTertiary = Gray.gray100           // White
val TertiaryContainer = Gray.gray97     // Gray Light
val OnTertiaryContainer = Gray.gray0    // Black

val Background = Black.black100         // White
val OnBackground = Black.black0         // Black
val Surface = Black.black100            // White
val OnSurface = Black.black0            // Black
val SurfaceVariant = Gray.gray90
val OnSurfaceVariant = Gray.gray0

val Error = Color(0xFFCB4848)
val OnError = Color(0xFFFFFFFF)
val ErrorContainer = Color(0xFFFFFFFF)
val OnErrorContainer = Color(0xFFCB4848)

val Outline = Blue.Blue37
val OutlineVariant = Color.White//Color(0xFF56B0F2)

fun StatusColor(statusCode: String): Color? = when(statusCode) {
    OrderStatuses.NEW -> PaletteLightColors.StatusIndicator.StatusNewColor
    OrderStatuses.INROAD -> PaletteLightColors.StatusIndicator.StatusInRoadColor
    OrderStatuses.ACTIVE -> PaletteLightColors.StatusIndicator.StatusActiveColor
    OrderStatuses.REJECTED -> PaletteLightColors.StatusIndicator.StatusRejectedColor
    OrderStatuses.CANCELED -> PaletteLightColors.StatusIndicator.StatusCanceledColor
    OrderStatuses.COMPLETED -> PaletteLightColors.StatusIndicator.StatusCompletedColor
    else -> null
}

    // Tones
object PaletteLightColors {
    object Blue {
        val Blue0  = Color(0xFF000000) // Black
        val Blue10 = Color(0xFF041d2f)
        val Blue20 = Color(0xFF083a5e)
        val Blue30 = Color(0xFF0c578d)
        val Blue37 = Color(0xFF006EBE) // Dark
        val Blue40 = Color(0xFF1074bc)
        val Blue50 = Color(0xFF1491eb)
        val Blue60 = Color(0xFF43a7ef)
        val Blue64 = Color(0xFF56B0F2) // Primary
        val Blue70 = Color(0xFF72bdf3)
        val Blue80 = Color(0xFFa1d3f7)
        val Blue90 = Color(0xFFd0e9fb)
        val Blue91 = Color(0xFFD0EBFF) // Light
        val Blue97 = Color(0xFFF1F5FE) // Light Background
        val Blue100= Color(0xFFFFFFFF) // White
    }
    object Gray {
        val gray0   = Color(0xFF000000)
        val gray10  = Color(0xFF1a1a1a)
        val gray20  = Color(0xFF333333)
        val gray30  = Color(0xFF4d4d4d)
        val gray40  = Color(0xFF666666)
        val gray50  = Color(0xFF808080)
        val gray58  = Color(0xFF959595)
        val gray60  = Color(0xFF999999)
        val gray70  = Color(0xFFb3b3b3)
        val gray80  = Color(0xFFcccccc)
        val gray85  = Color(0xFFD9D9D9)
        val gray90  = Color(0xFFe6e6e6)
        val gray97  = Color(0xFFF8F8F8)
        val gray100 = Color(0xFFFFFFFF)
    }
    object Black {
        val black0   = Color(0xFF000000)
        val black10  = Color(0xFF19181b)
        val black20  = Color(0xFF312f37)
        val black30  = Color(0xFF4a4752)
        val black40  = Color(0xFF625f6d)
        val black50  = Color(0xFF7b7788)
        val black60  = Color(0xFF9592a0)
        val black70  = Color(0xFFb0adb8)
        val black80  = Color(0xFFcac8d0)
        val black90  = Color(0xFFe5e4e7)
        val black100 = Color(0xFFFFFFFF)
    }
    object StatusIndicator {
        val StatusNewColor = Color(0xFF006EBE)
        val CloseToStartColor = Color(0xFF56B0F2)
        val StatusInRoadColor = Color(0xFFEDDE53)
        val StatusActiveColor = Color(0xFFFFB221)
        val StatusCompletedColor = Color(0xFF5AB215)
        val StatusCanceledColor = Color(0xFFCB4848)
        val StatusRejectedColor = Color(0xFFCB4848)
    }
}