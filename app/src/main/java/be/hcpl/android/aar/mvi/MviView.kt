package be.hcpl.android.aar.mvi

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
import be.hcpl.android.aar.common.AppScaffold
import be.hcpl.android.aar.common.CodeView
import be.hcpl.android.aar.common.model.Task
import be.hcpl.android.aar.common.model.TaskList
import be.hcpl.android.aar.common.TaskListView

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
            Text(
                text = "Overview of all tasks with MVVM",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            HorizontalDivider()
            TaskListView(
                tasks = tasks.list,
                onTaskSelected = onTaskSelected,
                modifier = Modifier.padding(vertical = 16.dp),
            )
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
        }
    }
}