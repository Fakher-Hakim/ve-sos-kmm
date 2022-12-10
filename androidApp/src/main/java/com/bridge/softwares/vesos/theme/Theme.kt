package com.bridge.softwares.vesos.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

private val lightThemeColors = VEThemeColors(
    primary = Blue,
    primaryDark = BlueDark,
    textOnGradient1 = Color.Black.copy(alpha = 0.87f),
    highlights = Pink,
    surface = Color.White,
    surfaceVariant = Color(0xFF050505),
    isLight = true
)

private val darkThemeColors = VEThemeColors(
    primary = Blue,
    primaryDark = BlueDark,
    textOnGradient1 = Color.Black.copy(alpha = 0.87f),
    highlights = Pink,
    surface = Color.White,
    surfaceVariant = Color(0xFF050505),
    isLight = false
)

private val typography = VETypography(
    title1 = Title1TextStyle,
    title2 = Title2TextStyle,
    body = BodyTextStyle,
    callout = CalloutTextStyle,
    footnote = FootnoteTextStyle,
    caption = CaptionTextStyle,
)

/**
 * Theme Composable that provides the VEThemeColors to it's content,
 * Also provides the VEThemeScope to allow access to screens/components in this module
 */
@Composable
fun VETheme(
    isDarkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colors = if (isDarkTheme) darkThemeColors else lightThemeColors

    val typography = typography

    val rippleIndication = rememberRipple()

    ProvideVEThemeColors(colors) {
        ProvideVETypography(typography) {
            CompositionLocalProvider(LocalIndication provides rippleIndication) {
                content()
            }
        }
    }
}

/**
 * Custom CompositionLocalProvider to propagate VEThemeColors down the Compose Tree
 */
@Composable
private fun ProvideVETypography(typography: VETypography, content: @Composable () -> Unit) {
    val rememberedTypography = remember { typography }
    rememberedTypography.update(typography)
    CompositionLocalProvider(LocalVETypography provides typography, content = content)
}

/**
 * Custom CompositionLocalProvider key for our ProvideVEThemeTypography method
 */
private val LocalVETypography = staticCompositionLocalOf<VETypography> {
    error("No VETypography provided")
}

/**
 * Custom CompositionLocalProvider to propagate VEThemeColors down the Compose Tree
 */
@Composable
private fun ProvideVEThemeColors(colors: VEThemeColors, content: @Composable () -> Unit) {
    val colorPalette = remember { colors.copy() }.apply { update(colors) }
    CompositionLocalProvider(LocalVEThemeColors provides colorPalette, content = content)
}

/**
 * Custom CompositionLocalProvider key for our ProvideVEThemeColors method
 */
private val LocalVEThemeColors = staticCompositionLocalOf<VEThemeColors> {
    error("No VEThemeColors provided")
}

/**
 * Custom CompositionLocalProvider key for our ProvideVETheme method
 */
val LocalVETheme = staticCompositionLocalOf<VETheme> {
    error("No VETheme provided")
}

@Stable
object VETheme {
    val colors: VEThemeColors
        @Composable get() = LocalVEThemeColors.current
    val typography: VETypography
        @Composable get() = LocalVETypography.current
}

/**
 * Class to store the text styles for our VE
 */
class VETypography(
    title1: TextStyle,
    title2: TextStyle,
    body: TextStyle,
    callout: TextStyle,
    footnote: TextStyle,
    caption: TextStyle,
) {
    var title1 by mutableStateOf(title1)
        private set

    var title2 by mutableStateOf(title2)
        private set

    var body by mutableStateOf(body)
        private set

    var callout by mutableStateOf(callout)
        private set

    var footnote by mutableStateOf(footnote)
        private set

    var caption by mutableStateOf(caption)
        private set

    fun update(newTypography: VETypography) {
        title1 = newTypography.title1
        title2 = newTypography.title2
        body = newTypography.body
        callout = newTypography.callout
        footnote = newTypography.footnote
        caption = newTypography.caption
    }
}

/**
 * Class to store the colors for our VETheme
 */
@Stable
class VEThemeColors(
    primary: Color,
    primaryDark: Color,
    textOnGradient1: Color,
    highlights: Color,
    surface: Color,
    surfaceVariant: Color,
    isLight: Boolean,
) {
    var primary by mutableStateOf(primary)
        private set

    var primaryDark by mutableStateOf(primary)
        private set

    var textOnGradient1 by mutableStateOf(textOnGradient1)
        private set

    var highlights by mutableStateOf(highlights)
        private set

    var surface by mutableStateOf(surface)
        private set

    var surfaceVariant by mutableStateOf(surfaceVariant)
        private set

    var isLight by mutableStateOf(isLight)
        private set

    fun update(newColors: VEThemeColors) {
        primary = newColors.primary
        primaryDark = newColors.primaryDark
        textOnGradient1 = newColors.textOnGradient1
        highlights = newColors.highlights
        surface = newColors.surface
        surfaceVariant = newColors.surfaceVariant
        isLight = newColors.isLight
    }

    fun update(
        primary: Color = this.primary,
        primaryDark: Color = this.primaryDark,
        textOnGradient1: Color = this.textOnGradient1,
        highlights: Color = this.highlights,
        surface: Color = this.surface,
        surfaceVariant: Color = this.surfaceVariant,
        isLight: Boolean = this.isLight,
    ): VEThemeColors {
        this.primary = primary
        this.textOnGradient1 = textOnGradient1
        this.highlights = highlights
        this.surface = surface
        this.surfaceVariant = surfaceVariant
        this.isLight = isLight
        return this
    }

    fun copy(): VEThemeColors = VEThemeColors(
        primary = primary,
        primaryDark = primaryDark,
        textOnGradient1 = textOnGradient1,
        highlights = highlights,
        surface = surface,
        surfaceVariant = surfaceVariant,
        isLight = isLight,
    )
}