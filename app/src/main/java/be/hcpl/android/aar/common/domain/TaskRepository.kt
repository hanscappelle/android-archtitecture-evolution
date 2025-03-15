package be.hcpl.android.aar.common.domain

import be.hcpl.android.aar.common.model.Task

class TaskRepository {

    // just mocked data really
    fun allTasks() = listOf(
        Task("incomplete task"),
        Task("another task"),
        Task("completed task", true),
        Task("last task"),
    )
}
