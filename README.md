# Weather

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/34e655757758466dbb00961b0c79e7be)](https://app.codacy.com/manual/enxy0/Weather?utm_source=github.com&utm_medium=referral&utm_content=enxy0/Weather&utm_campaign=Badge_Grade_Dashboard)

**Weather** — Android App written in Kotlin with MVVM architecture. In this project I tried to write clean and readable code.  
Minimalistic and light design makes it easy to see only the necessary data.

**Status:** In development *(1.0.1-release)* ⚙️

## Goals
-   [x] Display weather forecasts for different locations ☁️
-   [x] Save locations to favourites ⭐
-   [x] Support different units 📏
-   [x] Add tests for UI and code 🛠️
-   [x] Show 7-day forecast ⛅
-   [x] Provide documentation 📚
-   [x] Add animations 🔥
-   [x] Support night theme 🌙
-   [ ] Display weather forecast based on user location 📍

## Screenshots
<img  src="https://raw.githubusercontent.com/enxy0/Weather/development/screenshots/main.jpg?raw=true"  width=23% /> <img  src="https://raw.githubusercontent.com/enxy0/Weather/development/screenshots/favourite.jpg?raw=true"  width=23% /> <img  src="https://raw.githubusercontent.com/enxy0/Weather/development/screenshots/search.jpg?raw=true"  width=23% /> <img  src="https://raw.githubusercontent.com/enxy0/Weather/development/screenshots/settings.jpg?raw=true"  width=23% />

## Libraries & Dependencies
-   [Retrofit](https://github.com/square/retrofit) - Type-safe HTTP client for Android and Java by Square, Inc.
-   [Koin](https://github.com/InsertKoinIO/koin) - Pragmatic lightweight dependency injection framework for Kotlin.
-   [Room](https://developer.android.com/topic/libraries/architecture/room) - The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access.
-   [Gson](https://github.com/google/gson) - Gson is a Java library that can be used to convert Java Objects into their JSON representation.
-   [Material Design Components](https://material.io/develop/android/) - Material CardView, Bottom AppBar

## How to use this project
1.  Create API keys:
    -   [OpenWeatherMap](https://openweathermap.org/api) to fetch weather forecasts
    -   [OpenCage Geocoder](https://opencagedata.com/api) to find locations by name

2.  Edit `local.properties` file. Add your API keys here:

```kotlin
// ...
api_key_open_weather_map = "OPEN_WEATHER_MAP_API_KEY"
api_key_open_cage = "OPEN_CAGE_API_KEY"
```

3.  Rebuild project

## Credits
Icons made by [Freepik](https://www.flaticon.com/authors/freepik) from [www.flaticon.com](https://www.flaticon.com/) and modified by me :)
