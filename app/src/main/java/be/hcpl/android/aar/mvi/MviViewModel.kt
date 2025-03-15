package be.hcpl.android.aar.mvi

import androidx.lifecycle.ViewModel
import be.hcpl.android.aar.common.model.Task
import be.hcpl.android.aar.common.model.TaskList
import be.hcpl.android.aar.common.domain.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MviViewModel(
    private val taskRepository: TaskRepository, // injected
) : ViewModel() {

    // now we have a stateFlow for our tasks, fully thread safe
    val tasks: StateFlow<TaskList> by lazy { _tasks.asStateFlow() }

    // and this time properly hidden with a private mutable backing property
    private val _tasks: MutableStateFlow<TaskList> by lazy { MutableStateFlow(TaskList()) }

    fun showAllTasks() {
        // just update StateFlow
        _tasks.update { TaskList(taskRepository.allTasks()) }
    }

    fun toggleTask(task: Task) {
        // update the selected task
        val allTasks = tasks.value.list.map { if (it == task) it.copy(completed = !task.completed) else it }
        // and then update state again
        _tasks.update { TaskList(allTasks) }
    }
}