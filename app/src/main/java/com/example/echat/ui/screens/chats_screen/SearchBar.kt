package com.example.echat.ui.screens.chats_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.echat.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(viewModel: MainViewModel) {
    OutlinedTextField(
        value = viewModel.textState.value,
        onValueChange = { viewModel.onUpdateText(it) },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.Gray)
        },
        placeholder = { Text(text = "Search", color = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .clip(RoundedCornerShape(15.dp)),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color(0xFFF7F7FA),
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,

            )
    )
}