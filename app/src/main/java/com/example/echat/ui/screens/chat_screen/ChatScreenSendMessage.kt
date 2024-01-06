package com.example.echat.ui.screens.chat_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AttachFile
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.echat.R
import com.example.echat.ui.theme.ElementColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreenSendMessage(
    onSendMessage: (String) -> Unit,
    onPickerShow: () -> Unit
) {
    var textState by remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier
            .background(Color(0xFFF7F7FA)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, bottom = 25.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = textState,
                onValueChange = { textState = it },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = "Type...",
                        fontSize = 17.sp,
                        color = Color.Gray
                    )
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 17.sp
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .weight(1f)
                    .height(57.dp),
                singleLine = true,
                trailingIcon = {
                    IconButton(
                        onClick = { onPickerShow() },
                        modifier = Modifier.padding(end = 5.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.attach_file),
                            contentDescription = null,
                            tint = Color.Gray,
                        )
                    }
                }
            )
            Box(modifier = Modifier
                .weight(0.3f)
                .padding(start = 10.dp)) {
                SendButton(
                    onClick = {
                        onSendMessage(textState)
                        textState = ""
                    }
                )
            }
        }
    }
}

@Composable
private fun SendButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(ElementColor)
            .clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.send),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .padding(18.dp)
                .size(21.dp)
        )
    }
}