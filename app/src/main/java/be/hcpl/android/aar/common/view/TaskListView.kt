package be.hcpl.android.aar.common.view

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.hcpl.android.aar.common.model.Task
import be.hcpl.android.aar.common.theme.AppTheme

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

@Preview(showBackground = true)
@Composable
fun TaskListViewPreview() {
    AppTheme {
        TaskListView(listOf(Task("test1"), Task("test2")), {})
    }
}