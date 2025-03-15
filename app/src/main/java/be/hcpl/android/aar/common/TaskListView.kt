package be.hcpl.android.aar.common

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TaskListView(
        tasks: List<Task>,
        onTaskSelected: (Task) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(
            verticalArrangement = spacedBy(8.dp),
            modifier = modifier,
        ) {
            Text(
                text = "overview of all tasks",
                style = MaterialTheme.typography.titleLarge,
            )
            HorizontalDivider()
            tasks.forEach {
                TaskView(task = it,
                    onTaskSelected = onTaskSelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp))
            }
        }
    }

}