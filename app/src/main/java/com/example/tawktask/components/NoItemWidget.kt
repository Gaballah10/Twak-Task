package com.example.tawktask.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.example.tawktask.R

@Composable
fun NoItemWidget(){

        val context = LocalContext.current
        val ernestineFont = remember {
            FontFamily(
                typeface = ResourcesCompat.getFont(context, R.font.ernestine_font)!!
            )
        }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            val (instructionCard) = createRefs()

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(top = 10.dp, start = 8.dp, end = 8.dp)
                    .constrainAs(instructionCard) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }, backgroundColor = colorResource(id = R.color.grey)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_items),
                        Modifier
                            .padding(5.dp)
                            .wrapContentWidth(),
                        color = Color.White,
                        fontFamily = ernestineFont, fontSize = 14.sp
                    )
                }
            }
        }
}