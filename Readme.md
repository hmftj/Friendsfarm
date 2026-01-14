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
Here is the updated **README.md** reflecting the new **Agentic Hybrid** capabilities, the Splash Screen branding, and the technical bridge between your website and the Android OS.

---

# FriendsFarm Agentic Hybrid App

A high-performance Android portal for **FriendsFarm PVT LTD**. This app uses a "Hybrid Agentic" architecture, allowing the web-based livestock management system to interact directly with native Android hardware.

## 📱 App interface

| Splash Screen | Agentic Web View |
| --- | --- |
| **Branding**: FriendsFarm PVT LTD | **Engine**: Chromium-based WebView |
| **Credits**: Made by HMFTJ | **Feature**: Native Javascript Bridge |
| **Duration**: 3 Second Delay | **Loading**: Real-time Progress Bar |

---

## 🚀 What makes this "Agentic"?

Unlike a standard browser, this app includes a **Javascript Bridge**. This allows your website (`hmftj.com`) to send commands directly to the phone.

### The "Android" Object

The app injects a native object named `Android` into your website. You can trigger phone features from your HTML:

```html
<button onclick="Android.showToast('Livestock Saved!')">
    Save Data
</button>

```

---

## 🛠️ Updated Features

* **Integrated Splash Screen**: Professional entrance with "Powered by TARA" and "Made by HMFTJ" credits.
* **Persistent Navigation**: A bottom menu to switch between the main Livestock list and Contact pages.
* **Linear Loading Indicator**: A yellow progress bar appears at the top whenever a new web page is fetching data.
* **DOM Storage Enabled**: Supports modern web features (local storage, session storage) used by advanced PHP/JS frameworks.
* **Cleartext Support**: Configured to handle both `http` and `https` protocols for maximum compatibility.

---

## 📁 Installation Requirements

1. **URL Configuration**: Update the `livestockUrl` and `contactUrl` variables in `MainActivity.kt`.
2. **Manifest Permissions**:
* `INTERNET`: Required for web access.
* `usesCleartextTraffic`: Set to `true` to support standard HTTP.


3. **Dependencies**:
* `androidx.compose.material:material`
* `androidx.compose.ui:ui`



---

## 👨‍💻 Development Credits

* **Organization**: FriendsFarm PVT LTD
* **Lead Developer**: HMFTJ
* **Infrastructure**: Powered by TARA

---

### Pro-Tip for Web Implementation

To check if a user is visiting from your **Agentic App** or just a normal browser, use this Javascript in your website code:

```javascript
if (window.Android) {
   console.log("User is using the FriendsFarm App");
} else {
   console.log("User is using a standard browser");
}

```

**Would you like me to show you how to add a "Share" button to the App Bar so users can send livestock details to WhatsApp?**

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
