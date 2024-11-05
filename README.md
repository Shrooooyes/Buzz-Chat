# BUZZ-CHAT

BUZZ-CHAT is a group chat application that enables users to create and join group conversations, share text messages, and send images within a Firebase-based environment. It includes Firebase Authentication for secure login and registration, as well as Firebase Realtime Database for storing and retrieving chat messages in real time.

## Table of Contents
- [Features](#features)
- [Video](#video)
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Built With](#built-with)
- [License](#license)

## Features
- **User Authentication**: Sign up, login, and logout using Firebase Authentication.
- **Group Chat**: Join group chats automatically based on email prefix (determined in login).
- **Text Messaging**: Send and receive real-time text messages in a group chat.
- **Image Sharing**: Share images within the chat groups.
- **Real-time Database**: Chat messages are stored and updated in Firebase Realtime Database.

## Video
>https://github.com/user-attachments/assets/906de2d9-de83-4ec0-a2ab-44e879ab8c07



## Getting Started
Follow these instructions to set up and run the BUZZ-CHAT application locally on your Android device or emulator.

### Prerequisites
1. **Android Studio**: Install the latest version of [Android Studio](https://developer.android.com/studio).
2. **Firebase Project**:
   - Go to the [Firebase Console](https://console.firebase.google.com/) and create a new project.
   - Enable **Authentication** with **Email/Password** sign-in.
   - Enable **Firebase Realtime Database** and set the rules to:
     ```json
     {
       "rules": {
         ".read": "auth != null",
         ".write": "auth != null"
       }
     }
     ```
   - Add an **Android app** in Firebase with your application ID (found in `build.gradle`), and download the `google-services.json` file.
3. **Add Firebase SDK**: Place `google-services.json` in the `app` directory.

### Installation
1. Clone this repository:
   ```bash
   git clone https://github.com/your-repository/BUZZ-CHAT.git
2. Open the project in Android Studio.
3. Sync the project with Gradle to install dependencies.
4. Run the app on an emulator or physical device.

### Usage
  1. Register: Open the app and register using a valid email and password.
  2. Login: Enter registered email and password to log in.
  3. Group Chat: After login, you will be directed to the group chat activity where you can:
       - Send text messages.
       - Share images by selecting them from your device’s gallery.
       - View messages from other users in real time.

  ## Project Structure

```perl
com.example.chatapplication
│
├── MainActivity.java           # Main screen, checks if user is logged in
├── LoginActivity.java          # User login screen
├── RegisterActivity.java       # User registration screen
├── GroupChatActivity.java      # Main group chat activity
├── MessageAdapter.java         # RecyclerView Adapter for chat messages
├── Message.java                # Data model for chat messages
│
├── res/
│   ├── layout/
│   │   ├── activity_main.xml             # Layout for MainActivity
│   │   ├── activity_login.xml            # Layout for LoginActivity
│   │   ├── activity_registration.xml     # Layout for RegisterActivity
│   │   └── activity_group_chat.xml       # Layout for GroupChatActivity
│   └── values/
│       └── strings.xml                   # App string resources
│
└── AndroidManifest.xml                   # App manifest file
