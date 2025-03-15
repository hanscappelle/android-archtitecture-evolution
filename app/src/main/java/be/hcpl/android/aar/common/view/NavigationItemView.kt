package be.hcpl.android.aar.common.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import be.hcpl.android.aar.common.theme.AppTheme

@Composable
fun NavigationItem(text: String, navigateTo: (String) -> Unit) {
    Text(
        text,
        Modifier.clickable { navigateTo(text) }
    )
}

@Preview(showBackground = true)
@Composable
fun NavigationItemPreview() {
    AppTheme {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = SpaceEvenly,
        ) {
            NavigationItem("Text", {})
            NavigationItem("Text", {})
            NavigationItem("Text", {})
        }
    }
}
