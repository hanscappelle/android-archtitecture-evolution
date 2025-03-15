package be.hcpl.android.aar.mvi

import androidx.activity.ComponentActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

/**
 * MVI is really an evolution of MVVM that benefits from stateFlows and
 * reactive programming suitable for jetpack compose UI based views.
 */
class MviActivity : ComponentActivity() {

    // now it really gets interesting with the addition of flows in our ViewModel
    private val viewModel: MviViewModel by viewModel()
}