
# Difference Between MVC(Model View Controller) & MVP (Model View Presenter) &  MVVM(Model View ViewModel) architecture.

## MVC (Model View Controller) architecture
* **Model:** Model in Android can be data coming from within your application (including Shared Preferences), Database (in Cursor, or via other Data Access Object) or externally (via Cursor to other Data Contract) or we can say where we implement our business logic.
* **View:** is layout (XML file ,UI components/Widget) 
* **Controller:** is an Activity(Click Events, FindViewbyId etc)

## MVP (Model View Intent/Event)

**MVI = Model → View → Intent**

* **Model (State):** Immutable UI state. 
* **View:** Composables that render state.
* **Intent (Events):** User actions that mutate state through the ViewModel.

**Flow Diagram**
```text
 ┌───────────┐
 │   User    │
 └─────┬─────┘
       │ Intent (Click, Input, Scroll)
       ▼
 ┌─────────────┐
 │  ViewModel  │
 │ (Reducer)   │
 └─────┬───────┘
       │ Emits new State
       ▼
 ┌─────────────┐
 │    Model    │
 │ (UiState)   │
 └─────┬───────┘
       │
       ▼
 ┌─────────────┐
 │    View     │
 │ (Composable)│
 └─────────────┘

```mermaid
flowchart TD
    U[User] -->|Intent (Click, Input, Scroll)| VM[ViewModel<br/>(Reducer)]
    VM -->|Emits new State| M[Model<br/>(UiState)]
    M --> V[View<br/>(Composable)]
    V -->|User interacts again| U


* Unidirectional flow → makes UI predictable and testable.
* State is single source of truth.
* Events don’t directly mutate UI, only the ViewModel does.

**Diagram of MVI in Compose**

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/7f178888-3368-4e1e-beb0-380ba7d0633d" />


**🧠 State Management in Jetpack Compose: remember, rememberSaveable, rememberUpdatedState **

In Jetpack Compose, state drives UI. Choosing the right state holder is crucial for correctness, avoiding unnecessary recompositions, and ensuring UI behaves as expected across configuration changes.
