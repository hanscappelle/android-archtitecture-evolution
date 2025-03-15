package be.hcpl.android.aar.mvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.hcpl.android.aar.common.AppScaffold
import be.hcpl.android.aar.common.view.CodeView
import be.hcpl.android.aar.common.model.Task
import be.hcpl.android.aar.common.view.TaskListView
import be.hcpl.android.aar.common.view.TitleView
import be.hcpl.android.aar.common.navigate
import be.hcpl.android.aar.common.view.InfoTextView
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Mvvm architecture had the benefit of an Android specific ViewModel
 * to replace the Presenter. The ViewModel is fully lifecycle aware and
 * can hold LiveData that can be observed in the views.
 */
class MvvmActivity : ComponentActivity() {

    // important difference here is the introduction of a ViewModel
    private val viewModel: MvvmViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // now we have a viewModel with LiveData exposed
        viewModel.tasks.observeForever(::renderTasks)
        // and then trigger an update
        viewModel.showAllTasks()
    }

    fun renderTasks(tasks: List<Task>) {
        setContent {
            AppScaffold(
                navigateTo = { destination -> navigate(destination) }
            ) {
                Column(
                    verticalArrangement = spacedBy(8.dp),
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    TitleView(text = "Overview of all tasks with MVVM")
                    TaskListView(
                        tasks = tasks,
                        onTaskSelected = { task ->
                            // also here this has moved to the presenter
                            // so the presenter can again update the view
                            viewModel.toggleTask(task)
                        },
                        modifier = Modifier.padding(vertical = 16.dp),
                    )
                    TitleView("Sample Code")
                    InfoTextView("The replacement for a Presenter is a lifecycle aware ViewModel provided by Google.")
                    CodeView(
                        text = "class MvvmActivity : ComponentActivity() {\n" +
                                "\n" +
                                "    private val viewModel: MvvmViewModel by viewModel()\n" +
                                "\n" +
                                "    override fun onCreate(savedInstanceState: Bundle?) {\n" +
                                "        super.onCreate(savedInstanceState)\n" +
                                "        // now we have a viewModel with LiveData exposed\n" +
                                "        viewModel.tasks.observeForever(::renderTasks)\n" +
                                "        viewModel.showAllTasks()\n" +
                                "    }\n" +
                                "\n" +
                                "    fun renderTasks(tasks: List<Task>) {\n" +
                                "    //...\n" +
                                "}\n"
                    )
                    InfoTextView("The ViewModel can have LiveData that can be observed in the View.")
                    CodeView(
                        "class MvvmViewModel(\n" +
                                "    private val taskRepository: TaskRepository,\n" +
                                ") : ViewModel() {\n" +
                                "\n" +
                                "    // should really be hidden after a non mutable private property\n" +
                                "    val tasks: MutableLiveData<List<Task>> = MutableLiveData(emptyList())\n" +
                                "\n" +
                                "    fun showAllTasks() {\n" +
                                "        tasks.postValue(taskRepository.allTasks())\n" +
                                "    }\n" +
                                "}"
                    )
                }
            }
        }
    }
}