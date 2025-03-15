package be.hcpl.android.aar.mvvm

import androidx.activity.ComponentActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Mvvm architecture had the benefit of an Android specific ViewModel
 * to replace the Presenter. The ViewModel is fully lifecycle aware and
 * can hold LiveData that can be observed in the views.
 */
class MvvmActivity : ComponentActivity() {

    // important difference here is the introduction of a ViewModel
    private val viewModel: MvvmViewModel by viewModel()
}