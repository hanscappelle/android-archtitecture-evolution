package be.hcpl.android.aar.mvi

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.bundle.Bundle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import be.hcpl.android.aar.common.Task

import be.hcpl.android.aar.common.navigate
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

/**
 * MVI is really an evolution of MVVM that benefits from stateFlows and
 * reactive programming suitable for jetpack compose UI based views.
 */
class MviActivity : ComponentActivity() {

    // now it really gets interesting with the addition of flows in our ViewModel
    private val viewModel: MviViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // now we have a viewModel with StateFlow exposed
            val tasks by viewModel.tasks.collectAsStateWithLifecycle()
            MviView(
                tasks = tasks,
                onTaskSelected = { task -> viewModel.toggleTask(task) },
                navigateTo = { destination -> navigate(destination) },
            )
        }
        // trigger initial update, could be part of init on ViewModel also
        viewModel.showAllTasks()
    }
}