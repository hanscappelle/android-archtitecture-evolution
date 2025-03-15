package be.hcpl.android.aar.common.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.hcpl.android.aar.common.theme.AppTheme

@Composable
fun InfoTextView(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(horizontal = 16.dp),
    )
}

@Preview(showBackground = true)
@Composable
fun InfoTextViewPreview() {
    AppTheme {
        InfoTextView("Info text goes here and is typically longer content of multiple lines and what not.")
    }
}
