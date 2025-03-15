package be.hcpl.android.aar.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.hcpl.android.aar.common.theme.AppTheme

@Composable
fun CodeView(
    text: String,
) {
    Text(
        text = text,
        modifier = Modifier.background(color = Color.Black).padding(8.dp).fillMaxWidth(),
        style = MaterialTheme.typography.bodyLarge,
        fontFamily = FontFamily(Typeface(android.graphics.Typeface.MONOSPACE)),
        color = Color.White,
    )
}

@Preview(showBackground = true)
@Composable
fun CodeViewPreview() {
    AppTheme {
        CodeView("Some example code text here")
    }
}