This README is designed for the code provided above. It describes the **FriendsFarm** mobile application, which connects users to livestock data hosted on your web server.



# FriendsFarm Livestock App

A modern, lightweight Android application built with **Jetpack Compose**. This app serves as a dedicated mobile portal for the FriendsFarm livestock management system, providing real-time access to animal lists and contact services.

## 📱 App Preview

| Splash Screen | Main Livestock View |
| --- | --- |
|  |  |
| *Showcases Company Branding* | *Full-screen Web View of Livestock* |

> **Live URL:** [https://hmftj.com/ff/](https://hmftj.com/ff/)

---

## ✨ Features

* **Custom Splash Screen**: Professional entry featuring "FriendsFarm PVT LTD" branding.
* **Dual-Tab Navigation**:
* **Livestock**: Direct access to the animal inventory list.
* **Contact**: Quick navigation to the contact portal.


* **Integrated Web Engine**: High-performance WebView with JavaScript and DOM storage enabled.
* **Interactive Loading**: A visual progress bar at the top of the screen indicates page load status.
* **Company Credits**: Clear attribution to the developer and technology partner.

---

## 🛠️ Technical Stack

* **Language**: Kotlin
* **UI Framework**: Jetpack Compose
* **Web Integration**: Android WebKit (WebView)
* **Design Pattern**: Single Activity Architecture

---

## 🚀 Installation & Setup

1. **Clone the project** or copy the `MainActivity.kt` code into your Android Studio project.
2. **Permissions**: Ensure your `AndroidManifest.xml` includes the Internet permission:
```xml
<uses-permission android:name="android.permission.INTERNET" />

```


3. **HTTP Support**: If using non-HTTPS links, enable cleartext traffic in the manifest:
```xml
android:usesCleartextTraffic="true"

```


4. **Build**: Sync Gradle and run on your physical device or emulator.

---

## 🏗️ Credits

* **Made by**: **HMFTJ** (Hafiz Muhammad Furqan Tahir Jameel)
* **Powered by**: **TARA Technologies**
* **Project**: FriendsFarm PVT LTD

---

## 📄 License

© 2026 FriendsFarm PVT LTD. All rights reserved. Developed by HMFTJ.
