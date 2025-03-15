package be.hcpl.android.aar.mvp

import be.hcpl.android.aar.common.model.Task

interface View {

    fun renderTasks(tasks: List<Task>)

}