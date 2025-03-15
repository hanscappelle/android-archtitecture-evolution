package be.hcpl.android.aar.mvc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text

/**
 * In an MVC architecture applied to Android the Controller was often directly
 * implemented by the Activity or Fragment Android class. Note that the use of
 * anything Android framework specific will make testing difficult.
 */
class MvcController : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // this controller is responsible for getting data and sending it to the UI
        val allTasks =
        setContent {
            Text("Hello world!")
        }
    }
}