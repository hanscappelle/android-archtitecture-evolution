package be.hcpl.android.aar.mvp

import be.hcpl.android.aar.common.model.Task

/**
 * contract for the Presenter
 */
interface Presenter {

    var view: View?  // we need a reference to our view, these could be done with generics

    fun showAllTasks()

    fun toggleTask(task: Task)

}