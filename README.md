# GitHub Users Android App
This project is an Android application developed using Kotlin and Jetpack Compose. It fetches and displays a list of GitHub users and their details across two screens, supporting offline functionality with cached data and asynchronous refresh.

## Features
- Display a list of GitHub users: The app fetches and displays a list of GitHub users using the GitHub REST API.
- User details screen: Navigate to a detailed screen with more information about a specific user.
- Offline support: Cached data is displayed when the app is offline, and it refreshes in the background when the network is available.
- Navigation between screens: Implemented using Jetpack Compose Navigation.
- Room Database: Used for local caching of data for offline access.
- Asynchronous data fetching: Managed with Kotlin Coroutines.
- Dependency Injection: Managed with Koin.
- Image Loading: Images are loaded asynchronously using Coil.

## Tech Stack
- Language: Kotlin
- UI Framework: Jetpack Compose
- Networking: Ktor
- Caching/Database: Room
- Dependency Injection: Koin
- Coroutines: Kotlin Coroutines
- Image Loading: Coil
- Navigation: Jetpack Compose Navigation
