package com.example.echat.ui.screens.chat_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.echat.ui.theme.ElementColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun ChatScreenSendMessage(
    onSendMessage: (String) -> Unit
) {
    var textState by remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, bottom = 10.dp, top = 10.dp)
        ) {
            OutlinedTextField(
                value = textState,
                onValueChange = { textState = it },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFFECECEC),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = "Type...",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .weight(1f)
                    .height(50.dp),
                singleLine = true,
            )
            IconButton(
                onClick = {
                    onSendMessage(textState)
                    textState = ""
                },
                modifier = Modifier.weight(0.2f)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.Send,
                    contentDescription = null,
                    tint = ElementColor
                )
            }
        }
    }
}