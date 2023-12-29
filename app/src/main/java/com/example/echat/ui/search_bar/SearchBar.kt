package com.example.echat.ui.search_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.echat.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    viewModel: MainViewModel = hiltViewModel(),
    placeholderText: String = "Search",
    onSearch: () -> Unit,
) {
    OutlinedTextField(
        value = viewModel.textState.value,
        onValueChange = { viewModel.onUpdateText(it) },
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.Gray)
        },
        placeholder = { Text(text = placeholderText, color = Color.Gray, fontSize = 18.sp) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .clip(RoundedCornerShape(15.dp)),
        textStyle = TextStyle(fontSize = 18.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color(0xFFF7F7FA),
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch() }
        )
    )
}