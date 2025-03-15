package be.hcpl.android.aar.mvp

import be.hcpl.android.aar.common.domain.TaskRepository
import be.hcpl.android.aar.common.model.Task

class PresenterImpl(
    private val taskRepository: TaskRepository, // this is injected by koin
) : Presenter {

    // note that this view is optional here, a very minimal approach to check if attached or not
    override var view: View? = null

    // state has now moved to the presenter
    private var allTasks: List<Task> = emptyList()

    override fun showAllTasks() {
        allTasks = taskRepository.allTasks()
        // note safe operator used on view cause it could already be detached
        view?.renderTasks(allTasks)
    }

    override fun toggleTask(task: Task) {
        allTasks = allTasks.map { if (it == task) it.copy(completed = !task.completed) else it }
        // note safe operator used on view cause it could already be detached
        view?.renderTasks(allTasks)
    }
}