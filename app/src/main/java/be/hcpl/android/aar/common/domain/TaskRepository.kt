package be.hcpl.android.aar.common.domain

import be.hcpl.android.aar.common.model.Task

/**
 * A simple repository. This is where api calls could be triggered
 * or data stored locally.
 */
class TaskRepository {

    // just mocked data really
    fun allTasks() = listOf(
        Task("incomplete task"),
        Task("another task"),
        Task("completed task", true),
        Task("last task"),
    )
}
