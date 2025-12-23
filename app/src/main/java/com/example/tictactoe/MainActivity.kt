package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.max
import kotlin.math.min

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TicTacToeScreen()
            }
        }
    }
}

/* ----------------------------- VIEWMODEL ----------------------------- */

class TicTacToeViewModel : ViewModel() {

    var boardState by mutableStateOf(List(9) { "" })
        private set

    var gameResult by mutableStateOf<String?>(null)
        private set

    private val HUMAN = "X"
    private val AI = "O"

    fun onCellClicked(index: Int) {
        if (boardState[index].isNotEmpty() || gameResult != null) return

        boardState = boardState.toMutableList().also { it[index] = HUMAN }

        if (checkWinner(boardState, HUMAN)) {
            gameResult = "You Win!"
            return
        }

        if (isBoardFull(boardState)) {
            gameResult = "Draw!"
            return
        }

        val aiMove = findBestMove(boardState)
        boardState = boardState.toMutableList().also { it[aiMove] = AI }

        if (checkWinner(boardState, AI)) {
            gameResult = "AI Wins!"
            return
        }

        if (isBoardFull(boardState)) {
            gameResult = "Draw!"
        }
    }

    fun resetGame() {
        boardState = List(9) { "" }
        gameResult = null
    }

    /* ----------------------------- MINIMAX ----------------------------- */

    private fun findBestMove(board: List<String>): Int {
        var bestScore = Int.MIN_VALUE
        var bestMove = -1

        for (i in board.indices) {
            if (board[i].isEmpty()) {
                val newBoard = board.toMutableList()
                newBoard[i] = AI
                val score = minimax(newBoard, 0, false)
                if (score > bestScore) {
                    bestScore = score
                    bestMove = i
                }
            }
        }
        return bestMove
    }

    private fun minimax(board: List<String>, depth: Int, isMaximizing: Boolean): Int {
        if (checkWinner(board, AI)) return 10 - depth
        if (checkWinner(board, HUMAN)) return depth - 10
        if (isBoardFull(board)) return 0

        return if (isMaximizing) {
            var best = Int.MIN_VALUE
            for (i in board.indices) {
                if (board[i].isEmpty()) {
                    val newBoard = board.toMutableList()
                    newBoard[i] = AI
                    best = max(best, minimax(newBoard, depth + 1, false))
                }
            }
            best
        } else {
            var best = Int.MAX_VALUE
            for (i in board.indices) {
                if (board[i].isEmpty()) {
                    val newBoard = board.toMutableList()
                    newBoard[i] = HUMAN
                    best = min(best, minimax(newBoard, depth + 1, true))
                }
            }
            best
        }
    }

    private fun checkWinner(board: List<String>, player: String): Boolean {
        val wins = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            listOf(0, 4, 8), listOf(2, 4, 6)
        )
        return wins.any { pattern -> pattern.all { board[it] == player } }
    }

    private fun isBoardFull(board: List<String>) =
        board.none { it.isEmpty() }
}

/* ----------------------------- UI ----------------------------- */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TicTacToeScreen(viewModel: TicTacToeViewModel = viewModel()) {

    val board = viewModel.boardState
    val result = viewModel.gameResult

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "Tic Tac Toe",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.size(300.dp)
            ) {
                itemsIndexed(board) { index, cell ->
                    GameCell(
                        value = cell,
                        onClick = { viewModel.onCellClicked(index) }
                    )
                }
            }
        }

        if (result != null) {
            AlertDialog(
                onDismissRequest = {},
                confirmButton = {
                    Button(onClick = { viewModel.resetGame() }) {
                        Text("Restart Game")
                    }
                },
                title = { Text("Game Over") },
                text = { Text(result) }
            )
        }
    }
}

@Composable
fun GameCell(value: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .background(Color.LightGray)
            .clickable(enabled = value.isEmpty(), onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = if (value == "X") Color(0xFF1976D2) else Color(0xFFD32F2F)
        )
    }
}
