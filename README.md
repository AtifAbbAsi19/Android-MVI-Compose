# MVP (Model View Intent/Event)

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

```
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

**State Management in Jetpack Compose: remember, rememberSaveable, rememberUpdatedState**
In Jetpack Compose, state drives UI. Choosing the right state holder is crucial for correctness, avoiding unnecessary recompositions, and ensuring UI behaves as expected across configuration changes.

# 🧠 State Management in Jetpack Compose

Jetpack Compose manages UI with **state**. Choosing the right state holder is important for correctness, performance, and user experience.  

This guide explains **when to use**:  
- `remember`  
- `rememberSaveable`  
- `rememberUpdatedState`

---

## 1. `remember`

### 🔹 What it does
- Stores a value in the current **composition**.  
- Survives **recompositions**.  
- **Does not survive** configuration changes (rotation, dark mode) or process death.  

### 🔹 When to use
- For **temporary UI state** that only matters while the Composable is in memory.  
- Example: expand/collapse state, animation progress, toggle states.

### 🔹 Example
```kotlin
@Composable
fun Counter() {
    val count = remember { mutableStateOf(0) }

    Button(onClick = { count.value++ }) {
        Text("Count = ${count.value}")
    }
}
```

## 2. `rememberSaveable`

### 🔹 What it does
- Stores a value in the current **composition**.  
- Survives **recompositions**.  
- **Survive** configuration changes (rotation, dark mode).  

### 🔹 When to use
#### Use cases:
- Input fields (TextField), scroll positions, selections.
- Any UI state that must survive rotation or process recreation.

## 3. `rememberUpdatedState`

### 🔹 What it does
-Keeps a stable reference to a changing value for effects or lambdas that may outlive recompositions..  


### 🔹 When to use
#### Use cases:
- Long-lived effects (LaunchedEffect, SideEffect) needing the latest callback.
- Prevents stale closures when lambdas change over time.


## `PersistentListOf`
### 🔹 When to use
#### Use cases:
- when you "modify" it, you actually get a new copy that reuses most of the old structure under the hood. Efficient and immutable

🔹 It provides immutable & persistent collections:
- `persistentListOf()`
- `persistentSetOf()`
- `persistentMapOf()`
- `toPersistentList(), toPersistentSet(), etc.`

