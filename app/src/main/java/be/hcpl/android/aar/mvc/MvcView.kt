package be.hcpl.android.aar.mvc

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import be.hcpl.android.aar.common.Task
import be.hcpl.android.aar.common.TaskListView

/**
 * A View could be a classic XML based layout or classic #android.View implementation.
 * Here we opted for Composables since that is a lot faster for prototyping. In later
 * architecture patterns (MVVM and even more so in MVI) the declarative approach will
 * really shine.
 */
@Composable
fun MvcView(
    tasks: List<Task>,
    onTaskSelected: (Task) -> Unit,
    modifier: Modifier = Modifier,
) {
    TaskListView(tasks, onTaskSelected, modifier)
}


