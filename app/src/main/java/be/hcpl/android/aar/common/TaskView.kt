package be.hcpl.android.aar.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.hcpl.android.filmtag.ui.theme.AppTheme

@Composable
fun TaskView(
    task: Task,
) {
    Row(
        horizontalArrangement = spacedBy(8.dp)
    ) {
        Text(task.description)
        if (task.completed) {
            Image(
                imageVector = Icons.Default.Done,
                contentDescription = "task is completed",
                colorFilter = ColorFilter.tint(Color.Green),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskViewPreview() {
    AppTheme {
        TaskView(Task("completed task", true))
    }
}

@Preview(showBackground = true)
@Composable
fun TaskViewPreview2() {
    AppTheme {
        TaskView(Task("incomplete task"))
    }
}