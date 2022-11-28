# Movie App

### A simple application is built based on the architecture of MVVM with Clean Architecture.

## Features
- [x] View movie list.
- [x] Sort movie list by title or released date.
- [x] View movie details.
- [x] Add movie to Watchlist.
- [x] Remove movie from Watchlist.

## Architecture
<p align="center">
<img src="https://github.com/trongnk/movieapp/blob/main/screenshots/architecture.png">
</p>

## Data Flow
<p align="center">
<img src="https://github.com/trongnk/movieapp/blob/main/screenshots/data_flow.png">
</p>

## Project Structure
The source code is organized into 3 modules:
*   **Presentation:** interacts with the UI (Activity, Fragment, ViewModel, etc). This module also includes **domain** and **data**
*   **Data:** handles data sources used for the app. This module includes **domain** and implements the interfaces exposed by **domain**
*   **Domain:** defines business logic for the app. It is individual and does not depend on other modules

## Screenshot
<p align="center">
<img src="https://github.com/trongnk/movieapp/blob/main/screenshots/screenshot.png">
</p>

## Libraries
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
* [Room](https://developer.android.com/training/data-storage/room)
* [Retrofit2](https://github.com/square/retrofit)
* [Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html)
* [Data Binding](https://developer.android.com/topic/libraries/data-binding)