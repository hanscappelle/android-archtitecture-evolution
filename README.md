# A History of Android Application Architecture: From MVC to MVVM

This writing explores the evolution of native Android development, transitioning from an `MVC architecture`, where `Activities` and `Fragments` handled both domain and UI logic, to `MVVM`, introducing the `ViewModel` base class to promote a better separation of concerns (`SoC`).

The concepts discussed apply to both small and large applications, single- or multi-module, and are relevant for both XML layouts and declarative Jetpack Compose UI. Each architectural pattern covered here focuses on structuring the presentation layer of an Android app. While `Activities`, `Fragments`, and `ViewModels` are specific to the Android framework, the principles behind these patterns extend beyond Android development.

It’s important to note that in these design patterns, the term “Model” refers to code that contains data and business logic, not just a simple data class (or a `POJO` for those stuck in Java land). To keep these examples simple I did limit the Model implementation to a `Task` data class and added a `TaskRepository` that just returns some mocked data.

```
data class Task(
    val description: String,
    val completed: Boolean = false,
)
```
```
class TaskRepository {
    fun allTasks() = listOf(
        Task("incomplete task", false),
        Task("another task"),
        Task("completed task", true),
        Task("last task"),
    )
}
```

## Model-View-Controller (MVC)

`MVC` is an architectural pattern mostly associated with iOS development, though it’s not exclusive to mobile applications. It divides the presentation layer into three core components:

- `Model` – Responsible for data, business logic, and application state. It does not interact with the UI directly and only responds to requests from the Controller.
- `View` – Displays data received from the Controller and renders the UI. It also passes user interactions (events) back to the Controller. The View has no knowledge of the business logic. 
- `Controller` – Acts as a mediator between the View and Model. It processes UI events, updates the Model, and then informs the View accordingly. This results in a bi-directional data flow.

In Android development, Activities or Fragments often served as the Controller, leading to large classes with too many responsibilities. This tightly coupled architecture made it difficult to test and maintain due to the heavy reliance on Android platform-specific code.

```
class MvcControllerActivity : ComponentActivity() {

    private val taskRepository: TaskRepository by inject()

    private var allTasks: List<Task> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // this controller is responsible for getting data and sending it to the UI
        allTasks = taskRepository.allTasks()
        renderTasks(allTasks)
    }

    private fun renderTasks(tasks: List<Task>) {
        setContent {
            AppScaffold(navigateTo = { destination -> navigate(destination) }) {
                MvcView(
                    tasks = tasks,
                    onTaskSelected = { task ->
                        // this activity being the controller it handles
                        // click events from the view also
                        toggleTask(task)
                    }
                )
            }
        }
    }

    private fun toggleTask(task: Task) { 
        // this just toggles the state of that single task and updates the full list of tasks
        // note that for simplicity again we don't have any storage update
        allTasks = allTasks.map { if (it == task) it.copy(completed = !task.completed) else it }
        renderTasks(allTasks)
    }
}
```

## Model-View-Presenter (MVP)

`MVP` emerged as a solution to the issues in MVC by introducing a Presenter, moving most business logic away from the Controller into a separate class.

An interface or contract was then created between the Presenter and View. Helping in testing that Presenter using Mocked Views. Another interface was added as a contract between the Presenter and Model.

A powerful solution when combined with dependency injection frameworks like Koin that allowed injection based on these interfaces. However, MVP also introduced significant boilerplate code, and ultimately, the Presenter was still tied to the Android View lifecycle.

```
class MvpActivity : ComponentActivity(), View {

    // thanks to the interfaces this is easy to inject and change afterwards
    private val presenter: Presenter by inject()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // since a presenter is not lifecycle aware we have to manually
        // add/remove the view, this is a very basic approach
        presenter.view = this
        // this is the initial data
        presenter.showAllTasks()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // and remove it again when not attached
        presenter.view = null
    }

    override fun renderTasks(tasks: List<Task>) { /* ... */ }
}
```
```
interface View {
    fun renderTasks(tasks: List<Task>)
}
```
```
interface Presenter {
    var view: View?
    fun showAllTasks()
    fun toggleTask(task: Task)
}
```
```
class PresenterImpl(
    private val taskRepository: TaskRepository,
) : Presenter {

    override var view: View? = null

    private var allTasks: List<Task> = emptyList()

    override fun showAllTasks() {
        allTasks = taskRepository.allTasks()
        view?.renderTasks(allTasks)
    }

    override fun toggleTask(task: Task) {
        allTasks = allTasks.map { if (it == task) it.copy(completed = !task.completed) else it }
        view?.renderTasks(allTasks)
    }
}
```

## Model-View-ViewModel (MVVM)

`MVVM` was Google’s response to these issues, introducing the ViewModel, a lifecycle-aware component that replaces the Presenter while maintaining a similar structure to MVP.

The `ViewModel` is fully lifecycle aware surviving configuration changes and is responsible for UI specific data.
With Google’s shift to Kotlin-first development, Android development with MVVM has become much more efficient. Enhancements like Kotlin delegates, extensions, and lifecycle-aware components like LiveData and StateFlow have further simplified using this architecture, paving the way for unidirectional data flow (UDF).

```
class MvvmActivity : ComponentActivity() {

    // important difference here is the introduction of a ViewModel
    private val viewModel: MvvmViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // now we have a viewModel with LiveData exposed
        viewModel.tasks.observeForever(::renderTasks)
        // and then trigger an initial update
        viewModel.showAllTasks()
    }

    fun renderTasks(tasks: List<Task>) { /* ... */ }
}
```
```
class MvvmViewModel(
    private val taskRepository: TaskRepository,
) : ViewModel() {

    // this can be observed from the view and update here
    // should really be hidden after a non mutable property
    val tasks: MutableLiveData<List<Task>> = MutableLiveData(emptyList())

    fun showAllTasks() {
        tasks.postValue(taskRepository.allTasks())
    }

    fun toggleTask(task: Task) {
        val allTasks = tasks.value?.map { if (it == task) it.copy(completed = !task.completed) else it }.orEmpty()
        tasks.postValue(allTasks)
    }
}
```

## Model-View-Intent (MVI)

`MVI` is again an evolution of MVVM, this time aiming to solve the many ways data flows between presentation layer components with the introduction of unidirectional data flow (UDF) and immutability. Two concepts that are perfectly suited for reactive and declarative programming and easily achieved with Kotlin and Compose.

In this MVI pattern the `Model` represents the state of the application using immutable data classes and the absence of business logic. This is where I need to clarify that this Model is not the same Model from the MVC, MVP or MVVM pattern. Instead it's a simple UI state data class.

The `View` is still the View but now it observes the state changes and renders these to the screen. Again no business logic here.

The `Intent` are all the UI events, many of which are triggered by user interactions, and are handled by the ViewModel. Possibly resulting in more UI State changes.

This results in the following unidirectional data flow: `View → Intent → ViewModel → State → View`

With `StateFlow`, implementing this pattern is straightforward, making `LiveData` and `data binding` obsolete.

```
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.bundle.Bundle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel
//import kotlin.getValue // DO NOT import this one or you'll get errors, instead the compose lifecycle one is needed
import androidx.compose.runtime.getValue

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
```
```
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MviViewModel(
    private val taskRepository: TaskRepository,
) : ViewModel() {

    // an example of private mutable backing field
    private val _tasks: MutableStateFlow<TaskList> by lazy { MutableStateFlow(TaskList()) }
    // vs the exposed but immutable public field 
    val tasks: StateFlow<TaskList> by lazy { _tasks.asStateFlow() }

    fun showAllTasks() {
        _tasks.update { TaskList(taskRepository.allTasks()) }
    }

    fun toggleTask(task: Task) {
        val allTasks = tasks.value.list.map { if (it == task) it.copy(completed = !task.completed) else it }
        _tasks.update { TaskList(allTasks) }
    }
}
```

## Code Example

Some final snippets to get everything working. Like the required plugins in the main `build.gradle` file:
```
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
```
And the dependencies in the `app/build.gradle` file (you can check the project for all the versions):
```
dependencies {
    // main
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    // for injection
    implementation(libs.koin.android)
    // compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.material3)
    // tooling
    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.androidx.ui.tooling)
}
```

Dependency injection is done with koin, so manifest contains a specific Application:
```
<application
        android:name=".common.di.MainApplication"
        ...
        tools:targetApi="31">
```
```
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}
```
Module definitions
```
al appModule = module {
    viewModelOf(::MvvmViewModel)
    viewModelOf(::MviViewModel)
    factoryOf(::TaskRepository)
    factoryOf(::PresenterImpl) { bind<Presenter>() } // injected by interface here
}
```
And how something is injected then 
```
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SomeActivity: ComponentActivity() {
    private val presenter: Presenter by inject()
    private val viewModel: MvvmViewModel by viewModel()
    //...
}
```

## Thank you for reading

Fortunately, I haven’t been involved much in projects that rely heavily on Android-specific data binding (nor view binding). And with Jetpack Compose, I’m confident I won’t need to anytime soon.
