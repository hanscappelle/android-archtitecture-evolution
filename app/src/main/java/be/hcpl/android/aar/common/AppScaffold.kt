package be.hcpl.android.aar.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import be.hcpl.android.filmtag.ui.theme.AppTheme
import be.hcpl.android.filmtag.ui.theme.customColor2
import be.hcpl.android.filmtag.ui.theme.onPrimaryDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    title: String = "Architecture Demo App",
    content: @Composable () -> Unit,
) {
    AppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = title) },
                    colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = customColor2,
                        titleContentColor = onPrimaryDark,
                        navigationIconContentColor = onPrimaryDark,
                        actionIconContentColor = onPrimaryDark,
                    ),
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Bottom app bar",
                    )
                }
            },
            ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
fun AppScaffoldPreview() {
    AppScaffold { }
}