# Scale 📱

A modern Android app built with **Kotlin**, following the **MVVM** architecture. This application is designed to manage **Sales**, **Customers**, and **Items**, with data persisted locally using **Room Database**.

---

## 🏗️ Architecture

The app follows **MVVM (Model-View-ViewModel)** architecture with a clean separation of concerns:

```

com.example.scale
│
├── data
│   ├── dao           # Room DAO interfaces
│   ├── database      # AppDatabase singleton
│   ├── entities      # Data entities for Room
│   └── repository    # Repositories for data handling
│
└── ui
├── customers     # Customers screen + ViewModel
├── items         # Items screen + ViewModel
├── sales         # Sales screen + ViewModel
├── theme         # App theming (Material3)
└── Main & Home screens

````

---

##  Features

 **Add, update, and delete Customers**  

**Create and view Sales**  
 **Persistent local storage using Room**  
 **Separation of data, business logic, and UI**  
 **Clean and modular folder structure**  

---

## Tech Stack

- **Kotlin**
- **MVVM Architecture**
- **Room Database**
- **Jetpack Compose**
- **Android ViewModel**

---

##  Getting Started

1. Clone the repo:
   ```bash
   git clone https://github.com/perwriter/scalekt
````

2. Open the project in **Android Studio**.

3. Sync Gradle and run the app on an emulator or Android device.

---

##  Folder Breakdown

```
- dao/         → Room DAO interfaces for CRUD
- database/    → AppDatabase.kt to initialize Room
- entities/    → Data classes representing DB tables
- repository/  → Repositories that abstract data access
- ui/          → Separate folders for each screen and their ViewModels
```

