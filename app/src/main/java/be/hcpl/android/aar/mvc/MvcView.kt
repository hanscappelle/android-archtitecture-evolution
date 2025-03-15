package be.hcpl.android.aar.mvc

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.hcpl.android.aar.common.CodeView
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
    Column(
        verticalArrangement = spacedBy(8.dp),
        modifier = modifier.then(Modifier.verticalScroll(rememberScrollState()))
    ) {
        Text(
            text = "Overview of all tasks with MVC",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        HorizontalDivider()
        TaskListView(tasks, onTaskSelected, modifier)
        CodeView(
            text = "class MvcControllerActivity : ComponentActivity() {\n" +
                    "\n" +
                    "    private val taskRepository: TaskRepository by inject()\n" +
                    "\n" +
                    "    private var allTasks: List<Task> = emptyList()\n" +
                    "\n" +
                    "    override fun onCreate(savedInstanceState: Bundle?) {\n" +
                    "        super.onCreate(savedInstanceState)\n" +
                    "        // this controller is responsible for getting data and sending it to the UI\n" +
                    "        allTasks = taskRepository.allTasks()\n" +
                    "        renderTasks(allTasks)\n" +
                    "    }\n" +
                    "    //...\n" +
                    "}\n"
        )
    }
}


