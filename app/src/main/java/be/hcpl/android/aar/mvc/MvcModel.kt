package be.hcpl.android.aar.mvc

import be.hcpl.android.aar.common.Task

/**
 * A Model in the MVC and MVP patterns was a mix of data and business logic. For simplicity
 * we limited the implementation in this example to a data class and a Repository implementation
 * for data retrieval
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

