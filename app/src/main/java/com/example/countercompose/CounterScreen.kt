package com.example.countercompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countercompose.ui.theme.CounterComposeTheme

// ===== Penghubung UI dengan ViewModel (State Hoisting) =====
@Composable
fun CounterApp(
    modifier: Modifier = Modifier,
    viewModel: CounterViewModel = viewModel()
) {
    val counter by viewModel.counter
    val nameInput by viewModel.nameInput      // Challenge
    val savedName by viewModel.savedName      // Challenge

    CounterScreen(
        counter = counter,
        nameInput = nameInput,
        savedName = savedName,
        onIncrement = { viewModel.increment() },
        onDecrement = { viewModel.decrement() },
        onReset = { viewModel.reset() },
        onNameChange = { viewModel.onNameChange(it) },
        onSaveName = { viewModel.saveName() },
        modifier = modifier
    )
}

// ===== Layar utama (stateless) =====
@Composable
fun CounterScreen(
    counter: Int,
    nameInput: String,
    savedName: String,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit,
    onNameChange: (String) -> Unit,
    onSaveName: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Counter App",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        CounterDisplay(
            value = counter,
            name = savedName,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))

        CounterButtons(
            onDecrement = onDecrement,
            onIncrement = onIncrement,
            onReset = onReset
        )

        // ===== CHALLENGE: form nama + tombol Simpan =====
        Spacer(modifier = Modifier.height(8.dp))
        NameForm(
            name = nameInput,
            onNameChange = onNameChange,
            onSave = onSaveName
        )
    }
}

// ===== Area angka (Box) =====
@Composable
fun CounterDisplay(
    value: Int,
    name: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            color = Color(0xFFF5F5F5),
            shape = RoundedCornerShape(16.dp)
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Nilai Counter",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        )

        // Challenge: angka + nama ditumpuk vertikal di tengah Box
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value.toString(),
                fontSize = 120.sp,
                fontWeight = FontWeight.Bold
            )
            if (name.isNotEmpty()) {
                Text(
                    text = name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// ===== Deretan tombol (Row) =====
@Composable
fun CounterButtons(
    onDecrement: () -> Unit = {},
    onIncrement: () -> Unit = {},
    onReset: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onDecrement,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF44336)
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_remove),
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                Text("KURANG")
            }
            Button(
                onClick = onIncrement,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                Text("TAMBAH")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onReset,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_refresh),
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.width(ButtonDefaults.IconSpacing))
            Text("RESET")
        }
    }
}

// ===== CHALLENGE: form input Nama + tombol Simpan =====
@Composable
fun NameForm(
    name: String,
    onNameChange: (String) -> Unit,
    onSave: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Nama") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SIMPAN")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CounterScreenPreview() {
    CounterComposeTheme {
        CounterScreen(
            counter = 7,
            nameInput = "",
            savedName = "Binay",
            onIncrement = {},
            onDecrement = {},
            onReset = {},
            onNameChange = {},
            onSaveName = {}
        )
    }
}