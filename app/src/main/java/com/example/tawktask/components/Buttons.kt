package com.example.tawktask.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.example.tawktask.R

@Composable
fun SaveButton(modifier: Modifier, onSaveClicked: () -> Unit) {

    val context = LocalContext.current
    val ernestineFont = remember {
        FontFamily(
            typeface = ResourcesCompat.getFont(context, R.font.ernestine_font)!!
        )
    }

    Button(
        modifier = modifier
            .padding(top = 10.dp)
            .width(120.dp)
            .height(35.dp),
        onClick = { onSaveClicked() },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
    ) {
        Text(
            text = "Save",
            fontSize = 15.sp,
            color = Color.White,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            fontFamily = ernestineFont
        )
    }
}