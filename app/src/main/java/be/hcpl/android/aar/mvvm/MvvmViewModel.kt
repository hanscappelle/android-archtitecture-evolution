package be.hcpl.android.aar.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.hcpl.android.aar.common.model.Task
import be.hcpl.android.aar.common.domain.TaskRepository

class MvvmViewModel(
    private val taskRepository: TaskRepository, // injected
) : ViewModel() {

    // this can be observed from the view and update here
    // should be hidden after a non mutable backing property
    val tasks: MutableLiveData<List<Task>> = MutableLiveData(emptyList())

    fun showAllTasks() {
        // update state value
        tasks.postValue(taskRepository.allTasks())
    }

    fun toggleTask(task: Task) {
        // alter state
        val allTasks = tasks.value?.map { if (it == task) it.copy(completed = !task.completed) else it }.orEmpty()
        // and again update state value so observer can pick it up
        tasks.postValue(allTasks)
    }
}