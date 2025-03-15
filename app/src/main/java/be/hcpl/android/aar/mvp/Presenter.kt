package be.hcpl.android.aar.mvp

import be.hcpl.android.aar.common.Task
import be.hcpl.android.aar.common.TaskRepository

interface Presenter {

    var view: View?  // we need a reference to our view, these could be generics

    fun showAllTasks()

    fun toggleTask(task: Task)

}

class PresenterImpl(
    private val taskRepository: TaskRepository,
) : Presenter {

    override var view: View? = null

    private var allTasks: List<Task> = emptyList()

    override fun showAllTasks() {
        allTasks = taskRepository.allTasks()
        view?.renderTasks(allTasks)
    }

    override fun toggleTask(task: Task) {
        allTasks = allTasks.map { if (it == task) it.copy(completed = !task.completed) else it }
        // TODO we need to check if view is attached also
        view?.renderTasks(allTasks)
    }
}

