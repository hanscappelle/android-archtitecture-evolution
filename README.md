# A History of Android Application Architecture: From MVC to MVVM

This writing explores the evolution of native Android development, transitioning from an `MVC architecture`, where `Activities` and `Fragments` handled both domain and UI logic, to `MVVM`, introducing the `ViewModel` base class to promote a better separation of concerns (`SoC`).

The concepts discussed apply to both small and large applications, single- or multi-module, and are relevant for both XML layouts and declarative Jetpack Compose UI. Each architectural pattern covered here focuses on structuring the presentation layer of an Android app.

While `Activities`, `Fragments`, and `ViewModels` are specific to the Android framework, the principles behind these patterns extend beyond Android development.

It’s important to note that in these design patterns, the term “Model” refers to code that contains data and business logic, not just a simple data class (or a `POJO` for those stuck in Java land).

## Model-View-Controller (MVC)

`MVC` is an architectural pattern mostly associated with iOS development, though it’s not exclusive to mobile applications. It divides the presentation layer into three core components:

- `Model` – Responsible for data, business logic, and application state. It does not interact with the UI directly and only responds to requests from the Controller.
- `View` – Displays data received from the Controller and renders the UI. It also passes user interactions (events) back to the Controller. The View has no knowledge of the business logic. 
- `Controller` – Acts as a mediator between the View and Model. It processes UI events, updates the Model, and then informs the View accordingly. This results in a bi-directional data flow.

In Android development, Activities or Fragments often served as the Controller, leading to large classes with too many responsibilities. This tightly coupled architecture made it difficult to test and maintain due to the heavy reliance on Android platform-specific code.

## Model-View-Presenter (MVP)

`MVP` emerged as a solution to the issues in MVC by introducing a Presenter, moving most business logic away from the Controller into a separate class.

An interface or contract was then created between the Presenter and View. Helping in testing that Presenter using Mocked Views. Another interface was added as a contract between the Presenter and Model.

A powerful solution when combined with dependency injection frameworks like Koin that allowed injection based on these interfaces. However, MVP also introduced significant boilerplate code, and ultimately, the Presenter was still tied to the Android View lifecycle.

## Model-View-ViewModel (MVVM)

`MVVM` was Google’s response to these issues, introducing the ViewModel, a lifecycle-aware component that replaces the Presenter while maintaining a similar structure to MVP.

The `ViewModel` is fully lifecycle aware surviving configuration changes and is responsible for UI specific data.
With Google’s shift to Kotlin-first development, Android development with MVVM has become much more efficient. Enhancements like Kotlin delegates, extensions, and lifecycle-aware components like LiveData and StateFlow have further simplified using this architecture, paving the way for unidirectional data flow (UDF).

## Model-View-Intent (MVI)

`MVI` is again an evolution of MVVM, this time aiming to solve the many ways data flows between presentation layer components with the introduction of unidirectional data flow (UDF) and immutability. Two concepts that are perfectly suited for reactive and declarative programming and easily achieved with Kotlin and Compose.

In this MVI pattern the `Model` represents the state of the application using immutable data classes and the absence of business logic. This is where I need to clarify that this Model is not the same Model from the MVC, MVP or MVVM pattern. Instead it's a simple UI state data class.

The `View` is still the View but now it observes the state changes and renders these to the screen. Again no business logic here.

The `Intent` are all the UI events, many of which are triggered by user interactions, and are handled by the ViewModel. Possibly resulting in more UI State changes.

This results in the following unidirectional data flow: `View → Intent → ViewModel → State → View`

With `StateFlow`, implementing this pattern is straightforward, making `LiveData` and `data binding` obsolete.

Fortunately, I haven’t been involved much in projects that rely heavily on Android-specific data binding (nor view binding). And with Jetpack Compose, I’m confident I won’t need to anytime soon. Thanks for reading.
