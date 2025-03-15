package be.hcpl.android.aar.mvp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.hcpl.android.aar.common.AppScaffold
import be.hcpl.android.aar.common.CodeView
import be.hcpl.android.aar.common.model.Task
import be.hcpl.android.aar.common.TaskListView
import be.hcpl.android.aar.common.navigate
import org.koin.android.ext.android.inject

/**
 * For the MVP approach there is a View and Presenter interface added. The Activity,
 * Fragment or custom android.View would implement the View interface while a separate
 * Presenter implementation to extract logic and improve testability.
 */
class MvpActivity : ComponentActivity(), View {

    // thanks to the interfaces this is easy to inject and change afterwards
    private val presenter: Presenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // the presenter is not aware yet of the view,
        // and we should manually check if still attached
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // since a presenter is not lifecycle aware we have to manually
        // add/remove the view, this is a very basic approach
        presenter.view = this

        // we now have a presenter to manage our app state and business logic
        presenter.showAllTasks()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // and remove it again when not attached
        presenter.view = null
    }

    override fun renderTasks(tasks: List<Task>) {
        setContent {
            AppScaffold(
                navigateTo = { destination -> navigate(destination) }
            ) {
                Column(
                    verticalArrangement = spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 16.dp).verticalScroll(rememberScrollState()),
                ) {
                    Text(
                        text = "Overview of all tasks with MVP",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                    HorizontalDivider()
                    TaskListView(
                        tasks = tasks,
                        onTaskSelected = { task ->
                            // also here this has moved to the presenter
                            // so the presenter can again update the view
                            presenter.toggleTask(task)
                        },
                        modifier = Modifier.padding(vertical = 16.dp),
                    )
                    CodeView(
                        text = "class MvpActivity : ComponentActivity(), View {\n" +
                                "\n" +
                                "    private val presenter: Presenter by inject()\n" +
                                "\n" +
                                "    override fun onCreate(savedInstanceState: Bundle?) {\n" +
                                "        super.onCreate(savedInstanceState)\n" +
                                "        // the presenter is not aware yet of the view,\n" +
                                "        // and we should manually check if still attached\n" +
                                "    }\n" +
                                "\n" +
                                "    override fun onAttachedToWindow() {\n" +
                                "        super.onAttachedToWindow()\n" +
                                "        presenter.view = this\n" +
                                "        presenter.showAllTasks()\n" +
                                "    }\n" +
                                "\n" +
                                "    override fun onDetachedFromWindow() {\n" +
                                "        super.onDetachedFromWindow()\n" +
                                "        presenter.view = null\n" +
                                "    }\n" +
                                "\n" +
                                "    override fun renderTasks(tasks: List<Task>) {\n" +
                                "    //...\n" +
                                "}\n"

                    )
                }
            }
        }
    }

}