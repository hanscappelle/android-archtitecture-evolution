package be.hcpl.android.aar.common

class TaskRepository {

    // just mocked data really
    fun allTasks() = listOf(
        Task("incomplete task"),
        Task("another task"),
        Task("completed task", true),
        Task("last task"),
    )
}
