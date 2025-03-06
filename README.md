# eChat 📱💬

**eChat** is a modern Android chat application built with **Kotlin** and **Jetpack Compose**. It enables real-time communication between two users using **WebSockets**. Users can send **text messages, images, and voice messages** seamlessly.

## ✨ Features

- 💚 **Real-time Messaging** (WebSockets)
- 🔐 **User Authentication & Sessions**
- 🎨 **Beautiful UI with Jetpack Compose**
- 🖼️ **Image Sharing**
- 🎧 **Voice Messages**
- 🔔 **Push Notifications** (OneSignal)
- 📂 **Local Storage** (Room Database)
- 🎥 **Media Playback** (ExoPlayer)

&nbsp;

## 🔧 Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **Room Database**
- **Retrofit & OkHttp** (for API calls)
- **Ktor Client** (for WebSockets)
- **Hilt** (Dependency Injection)
- **OneSignal** (Push Notifications)
- **ExoPlayer** (Media playback)
- **Coil** (Image Loading)

&nbsp;

## 📸 Screenshots

_Coming Soon!_

&nbsp;

## 🛠️ Installation

### Prerequisites
- Android Studio **Giraffe** or later
- Kotlin **1.8+**
- A valid **OneSignal** API key (for push notifications)

### Clone Repository
```sh
git clone https://github.com/your-username/eChat.git
cd eChat
```

### Run the App
1. Open the project in **Android Studio**.
2. Sync Gradle dependencies.
3. Run the app on an **emulator or a physical device**.

&nbsp;

## 📚 Dependencies

```kotlin
// Jetpack Compose
implementation("androidx.compose.material3:material3")
implementation("androidx.navigation:navigation-compose:2.7.6")

// WebSockets & API
implementation("io.ktor:ktor-client-websockets:1.6.3")
implementation("com.squareup.retrofit2:retrofit:2.9.0")

// Database
implementation("androidx.room:room-runtime:2.5.2")
kapt("androidx.room:room-compiler:2.5.2")

// Push Notifications
implementation("com.onesignal:OneSignal:[5.0.0, 5.99.99]")
```

*(Full dependencies available in `build.gradle.kts`)*

&nbsp;

## 💎 API & Backend

The **backend** for eChat is a separate project built with **Ktor**. Ensure the backend is running before launching the app.

🔗 **Backend Repository:** [eChat Backend](https://github.com/bondar7/eChat_backend) 

&nbsp;

## 🙏 Contributing

Contributions, issues, and feature requests are welcome! Feel free to **fork** and submit pull requests.

&nbsp;

## 📜 License

MIT License © [Maksym Bondar](https://github.com/your-username)

&nbsp;

