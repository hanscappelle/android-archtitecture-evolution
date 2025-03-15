package be.hcpl.android.aar.mvp

import be.hcpl.android.aar.common.Task

interface View {

    fun renderTasks(tasks: List<Task>)

}