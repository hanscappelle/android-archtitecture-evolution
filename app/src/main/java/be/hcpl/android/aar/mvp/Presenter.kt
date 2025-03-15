package be.hcpl.android.aar.mvp

import be.hcpl.android.aar.common.model.Task
import be.hcpl.android.aar.common.domain.TaskRepository

interface Presenter {

    var view: View?  // we need a reference to our view, these could be generics

    fun showAllTasks()

    fun toggleTask(task: Task)

}

class PresenterImpl(
    private val taskRepository: TaskRepository,
) : Presenter {

    // note that this view is optional here, a very minimal approach to check if attached or not
    override var view: View? = null

    private var allTasks: List<Task> = emptyList()

    override fun showAllTasks() {
        allTasks = taskRepository.allTasks()
        view?.renderTasks(allTasks)
    }

    override fun toggleTask(task: Task) {
        allTasks = allTasks.map { if (it == task) it.copy(completed = !task.completed) else it }
        view?.renderTasks(allTasks)
    }
}

