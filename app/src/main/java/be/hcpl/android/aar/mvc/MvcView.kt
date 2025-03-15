package be.hcpl.android.aar.mvc

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.hcpl.android.aar.common.view.CodeView
import be.hcpl.android.aar.common.model.Task
import be.hcpl.android.aar.common.view.InfoTextView
import be.hcpl.android.aar.common.view.TaskListView
import be.hcpl.android.aar.common.view.TitleView

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
        TitleView(text = "Overview of all tasks with MVC")
        TaskListView(tasks, onTaskSelected, modifier)
        TitleView(text = "Sample Code")
        InfoTextView("Controller often implemented as Android Activity, Fragment or View. " +
                "Having both business and data logic and keeping state.")
        CodeView(
            text = "class MvcActivity : ComponentActivity() {\n" +
                    "\n" +
                    "    private val taskRepository: TaskRepository by inject()\n" +
                    "\n" +
                    "    private var allTasks: List<Task> = emptyList()\n" +
                    "\n" +
                    "    override fun onCreate(savedInstanceState: Bundle?) {\n" +
                    "        super.onCreate(savedInstanceState)\n" +
                    "        // controller receives data from model and sends it to UI\n" +
                    "        allTasks = taskRepository.allTasks()\n" +
                    "        renderTasks(allTasks)\n" +
                    "    }\n" +
                    "}\n"
        )
        InfoTextView("With a Model that can be as simple as a data class and a repository.")
        CodeView(
            text = "data class Task(\n" +
                    "    val description: String,\n" +
                    "    val completed: Boolean = false,\n" +
                    ")\n\n" +
                    "class TaskRepository {\n" +
                    "    fun allTasks() = listOf(Task(\"one task\"))\n" +
                    "}"
        )
        InfoTextView("Using composable View here for a quick demo.")
        CodeView(
            text = "@Composable\n" +
                    "fun MvcView(\n" +
                    "    tasks: List<Task>,\n" +
                    "    onTaskSelected: (Task) -> Unit,\n" +
                    "    modifier: Modifier = Modifier,\n" +
                    ") {\n" +
                    "    //...\n" +
                    "} "
        )
    }
}


