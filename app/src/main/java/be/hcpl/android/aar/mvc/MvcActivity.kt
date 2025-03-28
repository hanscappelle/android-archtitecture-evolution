package be.hcpl.android.aar.mvc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.hcpl.android.aar.common.AppScaffold
import be.hcpl.android.aar.common.model.Task
import be.hcpl.android.aar.common.domain.TaskRepository
import be.hcpl.android.aar.common.navigate
import org.koin.android.ext.android.inject

/**
 * In an MVC architecture applied to Android the Controller was often directly
 * implemented by the Activity or Fragment Android class. Note that the use of
 * anything Android framework specific will make testing difficult.
 */
class MvcActivity : ComponentActivity() {

    // repository injected directly into the Android Activity is a good sign something is NOK
    private val taskRepository: TaskRepository by inject()

    // same for application state, but this is how it was often done initially
    private var allTasks: List<Task> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // this controller is responsible for getting data
        allTasks = taskRepository.allTasks()
        // and sending it to the UI
        renderTasks(allTasks)
    }

    private fun renderTasks(tasks: List<Task>) {
        setContent {
            AppScaffold(
                navigateTo = { destination -> navigate(destination) }
            ) {
                MvcView(
                    tasks = tasks,
                    onTaskSelected = { task ->
                        // this activity being the controller it handles
                        // click events from the view also
                        toggleTask(task)
                    },
                    modifier = Modifier.padding(vertical = 16.dp),
                )
            }
        }
    }

    private fun toggleTask(task: Task) {
        // this just toggles the state of that single task and updates the full list of tasks
        // note that for simplicity again we don't have any storage update using the repository
        allTasks = allTasks.map { if (it == task) it.copy(completed = !task.completed) else it }
        renderTasks(allTasks)
    }

}