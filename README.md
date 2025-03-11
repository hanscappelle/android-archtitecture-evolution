# A History of Android Application Architecture from MVC to MVVM

This writing describes an evolution in native android development from an MVC Architecture with Activities and Fragments handling both domain and UI logic towards a better separation of concerns (SoC) with the introduction of a ViewModel base class and the MVVM Architecture.

All of the following can be applied to both large and small, single and multi module applications. Using XML layouts or declarative jetpack compose UI. Every pattern described is a way of organising code in the presentation layer of your app. 

Though Activities, Fragments and ViewModels are Android Framework specific all of this can be used much wider.

Note that the word `Model` in these design patterns refers to code containing data and business logic and is not just a data class (or POJO for people stuck in Java land).

## Model-View-Controller

Is an architecture that was (is ?) mostly used by iOS developers but not limited to mobile development in any way. 

It divides the presentation layer in the following 3 components: Model, View and Controller. 

The Model: has no knowledge of the presentation. It's responsible for data, business logic and application state and responds to requests from the Controller. 

The View: receives data from the controller and is responsible for rendering this data on screen and passing UI events back to the controller. It has no knowledge of the business logic. 

The Controller: is what connects these 2 together. It receives events from the View and updates the Model. The Updated model is then passed back to the View to update the UI. Note this bi directional data flow. 

In Android development this Controller was often implemented directly by the Activity or Fragment. Resulting in large classes with too much responsibilities strangled with Android platform specific code making it difficult to test these.

## Model-View-Presenter

A solution for this was introduced with the MVP Architecture adding a Presenter and moving most business logic from the Controller into a dedicated class. 

An interface or contract was then created between the Presenter and the View. Helping in testing that presenter using Mocked views. Another interface was added as a contract between the Presenter and Model. 

A powerful solution combined with dependency injection frameworks like Koin that allowed injection based on these interfaces. 

However also creating a lot of boilerplate code. And in the end the presenter was still bound to the lifespan of the Android Views. 

## Model-View-ViewModel

Or MVVM was an answer from Google with a new lifecycle aware component named the ViewModel. In essence still an evolution of the previous MVP Architecture but this time replacing the non Android specific Presenter.

The ViewModel is fully lifecycle aware surviving configuration changes and is responsible for UI specific data. 

In the recent years of Android development with a change towards a Kotlin first approach lots of improvements were made making development for native Android apps with this MVVM Architecture a blast. 

For example the addition of delegates and extensions to reference a ViewModel instance in your Activity. But also LiveData and StateFlows bringing us to the latest UDF approach.

## Model-View-Intent 

MVI is again an evolution of MVVM, this time aiming to solve the many ways data flows between presentation layer components with the introduction of unidirectional flow (UDF) and immutability. 

Two concepts that are perfectly suited for the current reactive and declarative programming and easily achieved with Kotlin and Compose. 

In this MVI patter the Model represents the state of the application using immutable data class and the absence of any business logic. This is where I need to clarify that this Model is the same Model from the MVC, MVP or MVVM pattern. Instead it's just a UI state data class. 

The View is still the View but not it observes the state changes and renders these to the screen. Again no business logic here. 

The Intent are all the UI events, many of which are triggered by user interactions, and are handled by the ViewModel, possibly resulting in more UI State changes. 

This represents the following uni directional flow: `View > Intent > ViewModel > State > View`.


Something that can be implemented using a StateFlow. Making LiveData (and data binding) obsolete.

Luckily I haven't been involved much in projects using Android specific data binding (nor view binding). And with compose UI I'm confident I won't need to anytime soon. 
