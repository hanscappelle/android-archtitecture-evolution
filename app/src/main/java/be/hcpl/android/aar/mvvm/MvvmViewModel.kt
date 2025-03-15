package be.hcpl.android.aar.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.hcpl.android.aar.common.Task
import be.hcpl.android.aar.common.TaskRepository

class MvvmViewModel(
    private val taskRepository: TaskRepository,
) : ViewModel() {

    // this can be observed from the view and update here
    // should really be hidden after a non mutable property
    val tasks: MutableLiveData<List<Task>> = MutableLiveData(emptyList())

    fun showAllTasks() {
        tasks.postValue(taskRepository.allTasks())
    }

    fun toggleTask(task: Task) {
        val allTasks = tasks.value?.map { if (it == task) it.copy(completed = !task.completed) else it }.orEmpty()
        tasks.postValue(allTasks)
    }
}