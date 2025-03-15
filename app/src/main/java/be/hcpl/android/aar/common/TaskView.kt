package be.hcpl.android.aar.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.hcpl.android.aar.common.model.Task
import be.hcpl.android.aar.common.theme.AppTheme

@Composable
fun TaskView(
    task: Task,
    onTaskSelected: (Task) -> Unit = {},
) {
    Row(
        horizontalArrangement = spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .height(48.dp).padding(horizontal = 16.dp).clickable { onTaskSelected(task) },
    ) {
        Text(
            text = task.description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
            fontWeight = if (!task.completed) FontWeight.Bold else FontWeight.Normal,
        )
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