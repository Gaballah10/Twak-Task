package com.example.tawktask.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tawktask.navigation.TawkScreens
import com.example.tawktask.R
import com.example.tawktask.theme.ModifiedDesign
import com.example.tawktask.theme.PrimaryDesignDark

@Composable
@Preview(showBackground = true)
fun MainAppBarContent(
    title: String = "Home",
    navController: NavController = rememberNavController()
) {

    val context = LocalContext.current
    val ernestineFont = remember {
        FontFamily(
            typeface = ResourcesCompat.getFont(context, R.font.ernestine_font)!!
        )
    }

    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                    fontFamily = ernestineFont
                )
            }
        },
        Modifier.background(
            if (isSystemInDarkTheme()) ModifiedDesign
            else PrimaryDesignDark/*color = colorResource(id = R.color.brown_design)*/
        ),
        actions = {
            IconButton(onClick = {
                navController.navigate(TawkScreens.MainScreen.name)
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = colorResource(id = R.color.white)
                )
            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}

@Composable
fun BackAppBar(
    title: String,
    modifier: Modifier,
    onBackArrowClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val ernestineFont = remember {
        FontFamily(
            typeface = ResourcesCompat.getFont(context, R.font.ernestine_font)!!
        )
    }

    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "arrow back",
                    tint = if (isSystemInDarkTheme()) Color.Black
                    else Color.White,
                    modifier = modifier.clickable { onBackArrowClicked.invoke() })
            }
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                ProvideTextStyle(value = MaterialTheme.typography.h6) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            color = if (isSystemInDarkTheme()) Color.Black
                            else Color.White,
                            text = title,
                            fontFamily = ernestineFont
                        )
                    }
                }
            }
        },
        Modifier.background(
            color = if (isSystemInDarkTheme()) PrimaryDesignDark
            else ModifiedDesign
        ),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}


fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG)
        .show()
}