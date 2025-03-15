package be.hcpl.android.aar.mvi

import androidx.lifecycle.ViewModel
import be.hcpl.android.aar.common.Task
import be.hcpl.android.aar.common.TaskList
import be.hcpl.android.aar.common.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MviViewModel(
    private val taskRepository: TaskRepository,
) : ViewModel() {

    // now we have a stateFlow for our tasks, fully thread safe
    private val _tasks: MutableStateFlow<TaskList> by lazy {
        MutableStateFlow(TaskList())
    }
    val tasks: StateFlow<TaskList> by lazy { _tasks.asStateFlow() }

    fun showAllTasks() {
        _tasks.update { TaskList(taskRepository.allTasks()) }
    }

    fun toggleTask(task: Task) {
        val allTasks = tasks.value.list.map { if (it == task) it.copy(completed = !task.completed) else it }
        _tasks.update { TaskList(allTasks) }
    }
}