package be.hcpl.android.aar.mvc

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import be.hcpl.android.aar.common.Task
import be.hcpl.android.aar.common.TaskView

/**
 * A View could be a classic XML based layout or classic #android.View implementation.
 * Here we opted for Composables since that is a lot faster for prototyping. In later
 * architecture patterns (MVVM and even more so in MVI) the declarative approach will
 * really shine.
 */
@Composable
fun MvcView(
    tasks: List<Task>,
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier) {
        Text("overview of all tasks")
        tasks.forEach {
            TaskView(it)
        }
    }
}


