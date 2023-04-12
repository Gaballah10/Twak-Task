package com.example.tawktask.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.example.tawktask.R


@Composable
@Preview(showBackground = true)
fun InternetNoConnectionComponent() {

    val context = LocalContext.current
    val ernestineFont = remember {
        FontFamily(
            typeface = ResourcesCompat.getFont(context, R.font.ernestine_font)!!
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.Red),
        Arrangement.Center, Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.no_internet_connections),
            color = Color.White,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
            fontFamily = ernestineFont
        )
    }
}