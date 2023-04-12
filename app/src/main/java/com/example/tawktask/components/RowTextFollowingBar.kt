package com.example.tawktask.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.example.tawktask.network.model.GithubUser

@Composable
fun RowTextFollowingBar(data: GithubUser) {

    val context = LocalContext.current
    val ernestineFont = remember {
        FontFamily(
            typeface = ResourcesCompat.getFont(context, R.font.ernestine_font)!!
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Text(
                text = "Followers : ",
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                fontFamily = ernestineFont
            )
            Text(
                text = data.followers.toString(),
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                fontFamily = ernestineFont
            )

        }
        Row {
            Text(
                text = "Following : ",
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                fontFamily = ernestineFont
            )
            Text(
                text = data.following.toString(),
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                fontFamily = ernestineFont
            )
        }
    }
}