package be.hcpl.android.aar.mvi

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.hcpl.android.aar.common.AppScaffold
import be.hcpl.android.aar.common.view.CodeView
import be.hcpl.android.aar.common.model.Task
import be.hcpl.android.aar.common.model.TaskList
import be.hcpl.android.aar.common.view.InfoTextView
import be.hcpl.android.aar.common.view.TaskListView
import be.hcpl.android.aar.common.view.TitleView

@Composable
fun MviView(
    tasks: TaskList,
    onTaskSelected: (Task) -> Unit,
    navigateTo: (String) -> Unit,
) {
    AppScaffold(
        navigateTo = navigateTo,
    ) {
        Column(
            verticalArrangement = spacedBy(8.dp),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            TitleView(text = "Overview of all tasks with MVI")
            TaskListView(
                tasks = tasks.list,
                onTaskSelected = onTaskSelected,
                modifier = Modifier.padding(vertical = 16.dp),
            )
            TitleView("Sample Code")
            InfoTextView("This final MVI approach has a ViewModel with StateFlow. That is converted here with collectAsStateWithLifecycle().")
            CodeView(
                text = "class MviActivity : ComponentActivity() {\n" +
                        "\n" +
                        "    private val viewModel: MviViewModel by viewModel()\n" +
                        "\n" +
                        "    override fun onCreate(savedInstanceState: Bundle?) {\n" +
                        "        super.onCreate(savedInstanceState)\n" +
                        "        setContent {\n" +
                        "            val tasks by viewModel.tasks.collectAsStateWithLifecycle()\n" +
                        "            MviView(\n" +
                        "                tasks = tasks,\n" +
                        "                onTaskSelected = { task -> viewModel.toggleTask(task) },\n" +
                        "                navigateTo = { destination -> navigate(destination) },\n" +
                        "            )\n" +
                        "        }\n" +
                        "        // trigger initial update, could be part of init on ViewModel also\n" +
                        "        viewModel.showAllTasks()\n" +
                        "    }\n" +
                        "}"
            )
            InfoTextView("Note how here the tasks StateFlow is properly hidden with a private MutableStateFlow field.")
            CodeView(
                "class MviViewModel(\n" +
                        "    private val taskRepository: TaskRepository,\n" +
                        ") : ViewModel() {\n" +
                        "\n" +
                        "    private val _tasks: MutableStateFlow<TaskList> by lazy {\n" +
                        "        MutableStateFlow(TaskList())\n" +
                        "    }\n" +
                        "    val tasks: StateFlow<TaskList> by lazy { _tasks.asStateFlow() }\n" +
                        "\n" +
                        "    fun showAllTasks() {\n" +
                        "        _tasks.update { TaskList(taskRepository.allTasks()) }\n" +
                        "    }\n" +
                        "}"
            )
        }
    }
}