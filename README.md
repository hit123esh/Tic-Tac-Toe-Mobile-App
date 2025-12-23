# Tic-Tac-Toe Mobile App (AI Powered)

A single-player Tic-Tac-Toe Android application built using Kotlin and Jetpack Compose, featuring an unbeatable AI opponent powered by the Minimax algorithm.

This project demonstrates modern Android development practices, clean architecture, and fundamental game AI concepts.

---

## Project Overview

The application allows a human player to play Tic-Tac-Toe against an AI opponent.  
The human always plays first as `X`, while the AI plays as `O`.

The AI uses the Minimax algorithm to evaluate all possible future game states and always chooses the optimal move, ensuring that it never loses.

---

## Features

- Single-player mode (Human vs AI)
- Unbeatable AI using the Minimax algorithm
- Modern UI built with Jetpack Compose (Material 3)
- MVVM architecture with ViewModel-based state management
- Game result dialog for win, loss, or draw
- Restart game functionality
- Clean and responsive user interface

---

## AI Logic – Minimax Algorithm

The Minimax algorithm works by simulating every possible move from the current game state until a terminal state is reached.

Scoring logic:
- +10 → AI win
- -10 → Human win
- 0 → Draw

The algorithm recursively explores all possible game outcomes and selects the move that maximizes the AI’s chance of winning while minimizing the opponent’s advantage. As a result, the AI always plays optimally and cannot be defeated.

---

## Tech Stack

- Language: Kotlin
- UI Framework: Jetpack Compose (Material 3)
- Architecture: MVVM (Model–View–ViewModel)
- State Management: mutableStateOf
- Build System: Gradle (Kotlin DSL)
- IDE: Android Studio
- Minimum SDK: API 24 (Android 7.0)

---

## Project Structure

app/
└── src/main/java/com/example/tictactoe/
└── MainActivity.kt


The UI, ViewModel, and Minimax game logic are intentionally kept in a single file to keep the project simple and easy to understand.

---

## How to Run the App

### Using Android Studio
1. Clone the repository:
   ```bash
   git clone https://github.com/hit123esh/Tic-Tac-Toe-Mobile-App.git


Screenshots:


<img width="198" height="436" alt="Screenshot 2025-12-23 at 11 50 12 AM" src="https://github.com/user-attachments/assets/f1966892-5fc0-446b-8858-0468f18de407" />


<img width="197" height="436" alt="Screenshot 2025-12-23 at 11 50 29 AM" src="https://github.com/user-attachments/assets/3f143d23-b697-4e64-9369-232403809213" />

