package be.hcpl.android.aar.mvp

import be.hcpl.android.aar.common.model.Task

/**
 * Contract for the View
 */
interface View {

    fun renderTasks(tasks: List<Task>)

}