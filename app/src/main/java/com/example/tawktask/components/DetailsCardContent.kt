package com.example.tawktask.components

import androidx.compose.foundation.layout.*
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
import com.example.tawktask.network.model.GithubUser

@Composable
fun DetailsCardContent(data : GithubUser){

    val context = LocalContext.current
    val ernestineFont = remember {
        FontFamily(
            typeface = ResourcesCompat.getFont(context, R.font.ernestine_font)!!
        )
    }
    Column(Modifier.fillMaxWidth().wrapContentHeight()) {
        Row {
            Text(modifier = Modifier.padding(start = 10.dp,top = 10.dp),
                text = "Name : ",
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                fontFamily = ernestineFont
            )
            Text(modifier = Modifier.padding(top = 10.dp),
                text = data.login.toString(),
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                fontFamily = ernestineFont
            )
        }
        Row {
            Text(modifier = Modifier.padding(start = 10.dp,top = 10.dp),
                text = "Company : ",
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                fontFamily = ernestineFont
            )
            Text(modifier = Modifier.padding(top = 10.dp),
                text = data.company.toString(),
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                fontFamily = ernestineFont
            )
        }

        Row {
            Text(modifier = Modifier.padding(start = 10.dp,top = 10.dp, bottom = 10.dp),
                text = "Blog : ",
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                fontFamily = ernestineFont
            )
            Text(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                text = data.blog.toString(),
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                fontFamily = ernestineFont
            )
        }
    }
}