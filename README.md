# 💸 Budget Tracker App

A minimalist and intuitive mobile application built with **Kotlin** and **Jetpack Compose** that helps users track their income and expenses, categorize their spending, and gain basic financial insights. Data is securely stored using **Firebase Firestore**.

---

## 📱 Features

- **Track Income**
  - Add income entries with descriptions and amounts.

- **Track Expenses**
  - Add expenses with descriptions, amounts, categories, and dates.
  - Choose from a predefined list of common categories.

- **Smart Category Selector**
  - Visually rich category cards with icons and dynamic backgrounds.
  - Clear selection feedback when choosing a category.

- **Date Selection**
  - Pick expense date using a native date picker dialog.

- **Summary Dashboard**
  - View total income and total expenses for the current month.
  - Get a breakdown of expenses by category.
  - Visual pie chart representation of spending distribution.

- **Firebase Integration**
  - All transactions are stored and retrieved from **Firebase Firestore**.

---

## 🧰 Technologies

- [Kotlin](https://kotlinlang.org/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Firebase Firestore](https://firebase.google.com/docs/firestore)

---

## 📸 Screenshots

<p align="center">
  <img src="https://github.com/user-attachments/assets/94894ecd-2c8d-48e6-b158-fc65072d0329" width="250"/>
  <img src="https://github.com/user-attachments/assets/445f0ceb-b1c4-4c71-93d5-64d4c66b276c" width="250"/>
  <img src="https://github.com/user-attachments/assets/cec91e05-1c97-43dc-8b93-a493dbf5c13a" width="250"/>
  <img src="https://github.com/user-attachments/assets/3db048b8-a554-491b-bbd6-08b14cf11186" width="250"/>
  <img src="https://github.com/user-attachments/assets/da7fe1ba-a2fe-42d4-a078-a73b28337791" width="250"/>
</p>

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- Firebase account and project
- Android SDK 33+

### Firebase Setup

1. Go to [Firebase Console](https://console.firebase.google.com/) and create a new project.
2. Add a new Android app to the project and download the `google-services.json` file.
3. Place the `google-services.json` file inside the `/app` directory.
4. Enable **Cloud Firestore** in the Firebase Console.

### Running the App

1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/budget-tracker-app.git
   cd budget-tracker-app
