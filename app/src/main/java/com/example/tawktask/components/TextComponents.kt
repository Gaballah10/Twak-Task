package com.example.tawktask.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.example.tawktask.R

@Composable
fun MedTextComponents(text: String) {
    val context = LocalContext.current
    val ernestineFont = remember {
        FontFamily(
            typeface = ResourcesCompat.getFont(context, R.font.ernestine_font)!!
        )
    }

    Text(
        modifier = Modifier.padding(start = 2.dp, top = 10.dp , bottom = 10.dp),
        text = text,
        color = if (isSystemInDarkTheme()) Color.White
        else Color.Black,
        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
        fontFamily = ernestineFont
    )
}

@Composable
fun SmallTextComponents(text: String) {
    val context = LocalContext.current
    val ernestineFont = remember {
        FontFamily(
            typeface = ResourcesCompat.getFont(context, R.font.ernestine_font)!!
        )
    }

    Text(
        modifier = Modifier.padding(start = 10.dp, top = 10.dp , bottom = 10.dp),
        text = text,
        color = Color.Black,
        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp),
        fontFamily = ernestineFont
    )
}

@Composable
fun LargeTextComponents(text: String) {
    val context = LocalContext.current
    val ernestineFont = remember {
        FontFamily(
            typeface = ResourcesCompat.getFont(context, R.font.ernestine_font)!!
        )
    }

    Text(
        modifier = Modifier.padding(start = 2.dp, top = 10.dp , bottom = 10.dp),
        text = text,
        color = Color.Black,
        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
        fontFamily = ernestineFont
    )
}