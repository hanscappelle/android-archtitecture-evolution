package be.hcpl.android.aar.common

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.hcpl.android.aar.common.model.Task

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
        tasks.forEach {
            TaskView(
                task = it,
                onTaskSelected = onTaskSelected,
            )
        }
    }
}