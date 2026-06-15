# SmartBudget App

## Overview

SmartBudget is an Android budgeting application developed in Kotlin using Android Studio. The application helps users track daily expenses, monitor spending habits, set financial goals, and improve personal money management. 

---

## Purpose

The purpose of SmartBudget is to assist users in:

* Recording expenses quickly and efficiently.
* Monitoring spending by category.
* Setting budget goals.
* Visualising spending patterns.
* Encouraging responsible spending through gamification.

---

# Features

## Core Features

### User Authentication

* User registration (Sign Up)
* User Login
* User-specific expense records

### Expense Tracking

* Add expenses
* Enter amount, title, and category
* Automatically record dates
* View all expenses

### Receipt Attachment

* Attach receipt images to expense entries.
* Store image references with expenses.

### Expense Filtering

* Filter expenses between selected dates.
* View spending for specific periods.

### Category Totals

* Automatically calculate total spending per category.

### Budget Goals

* Set minimum spending goals.
* Set maximum spending goals.
* Monitor performance against goals.

---

# Final POE Features

## Spending Graph

The application includes a graph displaying:

* Spending per category.
* Minimum spending goal.
* Maximum spending goal.

The graph allows users to visually compare spending against budget goals.

### Technologies Used

* MPAndroidChart

---

## Budget Progress Tracker

A visual budget tracker displays:

* Percentage of budget used.
* Spending status.
* Budget health score.

Possible statuses include:

* Within Budget
* Below Minimum Goal
* Over Budget

---

## Gamification

The application rewards users for consistent expense tracking.

### Badges

🎯 Beginner
Awarded when the user starts using the app.

🎉 First Expense
Awarded after recording the first expense.

⭐ Smart Saver
Awarded after recording 10 expenses.

🏆 Budget Master
Awarded after recording 20 expenses.

### XP and Levels

Users earn:

* 10 XP per expense recorded.

Levels increase as XP grows:

| Level   | XP Required |
| ------- | ----------- |
| Level 1 | 0 XP        |
| Level 2 | 50 XP       |
| Level 3 | 100 XP      |
| Level 4 | 200 XP      |
| Level 5 | 300 XP      |

---

# Custom Feature 1

## Savings Goal Tracker

Users can:

* Create a savings goal.
* Save target amounts.
* Monitor progress towards savings objectives.

This feature encourages long-term financial planning.

---

# Custom Feature 2

## Receipt Management

Users can attach receipt images when recording expenses.

Benefits:

* Proof of purchases.
* Improved expense tracking.
* Easier financial record keeping.

---

# Database

The application uses Room Database for offline data storage.

### Stored Information

* Users
* Expenses
* Categories
* Receipt image paths
* Budget goals

---

# Design Considerations

The user interface was designed to be:

* Simple
* Modern
* Easy to navigate
* Mobile friendly

Design improvements include:

* Consistent colours
* Clear navigation buttons
* Progress indicators
* Graphical spending analysis

---

# Technologies Used

* Kotlin
* Android Studio
* Room Database
* RecyclerView
* SharedPreferences
* MPAndroidChart
* GitHub
* GitHub Actions

---

# GitHub Actions

GitHub Actions was implemented to:

* Automatically build the application.
* Run tests.
* Verify project integrity after each push.

This ensures the application can build successfully on environments other than the developer machine.

---
# Screenshots

1. Login Screen <img width="372" height="785" alt="image" src="https://github.com/user-attachments/assets/3b8e5473-d7ba-45fe-9049-55a5044d7981" />

2. Dashboard <img width="376" height="780" alt="image" src="https://github.com/user-attachments/assets/2bf3e597-59bf-4505-be95-6671feab2348" />

3. Add Expense Screen <img width="378" height="777" alt="image" src="https://github.com/user-attachments/assets/780e46be-0f11-48b2-b40c-8c90fed3fdb9" />

4. Goals Screen <img width="342" height="291" alt="image" src="https://github.com/user-attachments/assets/71e0af8e-cf4d-4863-b377-bbb10ee4617b" />

5. Graph Screen <img width="372" height="448" alt="image" src="https://github.com/user-attachments/assets/f6ea3c4b-ebcb-495a-9469-345a2090546c" />

6. Savings Tracker <img width="367" height="372" alt="image" src="https://github.com/user-attachments/assets/735582d2-cfdd-4641-a9d6-83d6a8f93b31" />

7. Gamification Badges <img width="372" height="783" alt="image" src="https://github.com/user-attachments/assets/04edc60a-c3b0-4350-a543-0b26009bcd88" />


---

# APK

The APK file is included within the repository submission.

---

# Demonstration Video

Video Link:



---

# How to Run

1. Clone the repository.
2. Open the project in Android Studio.
3. Sync Gradle files.
4. Build the project.
5. Run on an Android device.

---



# References

Android Developers. Available at: https://developer.android.com

MPAndroidChart Documentation. Available at: https://github.com/PhilJay/MPAndroidChart

Room Database Documentation. Available at: https://developer.android.com/training/data-storage/room

---

# Author

Aneeqah Fisher

Portfolio of Evidence


## APK
APK file included in repository

## Author
Aneeqah Fisher
