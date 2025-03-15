package be.hcpl.android.aar.mvc

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.hcpl.android.aar.common.AppScaffold
import be.hcpl.android.aar.common.Task
import be.hcpl.android.aar.common.TaskRepository
import be.hcpl.android.aar.mvi.MviActivity
import be.hcpl.android.aar.mvp.MvpActivity
import be.hcpl.android.aar.mvvm.MvvmActivity
import org.koin.android.ext.android.inject

/**
 * In an MVC architecture applied to Android the Controller was often directly
 * implemented by the Activity or Fragment Android class. Note that the use of
 * anything Android framework specific will make testing difficult.
 */
class MvcControllerActivity : ComponentActivity() {

    private val taskRepository: TaskRepository by inject()

    private var allTasks: List<Task> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // this controller is responsible for getting data and sending it to the UI
        allTasks = taskRepository.allTasks()
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
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }

    private fun toggleTask(task: Task) {
        // this just toggles the state of that single task and updates the full list of tasks
        // note that for simplicity again we don't have any storage update
        allTasks = allTasks.map { if (it == task) it.copy(completed = !task.completed) else it }
        renderTasks(allTasks)
    }

    private fun navigate(destination: String) {
        when(destination){
            "MVC" -> startActivity(Intent(this, MvcControllerActivity::class.java))
            "MVP" -> startActivity(Intent(this, MvpActivity::class.java))
            "MVVM" -> startActivity(Intent(this, MvvmActivity::class.java))
            "MVI" -> startActivity(Intent(this, MviActivity::class.java))
        }
    }
}