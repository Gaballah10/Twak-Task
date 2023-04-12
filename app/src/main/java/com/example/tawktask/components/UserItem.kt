package com.example.tawktask.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Note
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tawktask.R
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import coil.compose.rememberImagePainter
import com.example.tawktask.network.model.GithubUser
import com.example.tawktask.theme.ModifiedDesign
import com.example.tawktask.theme.PrimaryDesignDark
import com.example.tawktask.theme.White

@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    localUsersList: List<GithubUser>,
    onDetailsClicked: (GithubUser) -> Unit,
    githubUser: GithubUser
) {
    val context = LocalContext.current
    val ernestineFont = remember {
        FontFamily(
            typeface = ResourcesCompat.getFont(context, R.font.ernestine_font)!!
        )
    }

    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .fillMaxWidth()
            .padding(6.dp)
            .height(130.dp)
            .clickable {
                onDetailsClicked.invoke(githubUser)
            }, elevation = 8.dp,
        backgroundColor =  if (isSystemInDarkTheme())  White
        else White

    ) {
        ConstraintLayout {
            val (userImage, userText, detailsText, detailUser2, detailUser3, note) = createRefs()
            val startGuideLine = createGuidelineFromStart(10.dp)
            val endLine = createGuidelineFromEnd(20.dp)

            Image(
                painter = rememberImagePainter(githubUser.avatar_url.toString()),
                contentDescription = "User Image",
                modifier
                    .clip(RoundedCornerShape(10.dp))
                    .width(100.dp)
                    .height(100.dp)

                    .constrainAs(userImage) {
                        start.linkTo(startGuideLine)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                contentScale = ContentScale.Crop
            )
            Text(
                text = githubUser.login!!,
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .padding(start = 10.dp)
                    .constrainAs(userText) {
                        start.linkTo(userImage.end)
                        top.linkTo(userImage.top)
                    },
                color = Color.Black,
                fontSize = 14.sp,
                maxLines = 2,
                fontStyle = FontStyle.Normal,
                fontFamily = ernestineFont,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = githubUser.type!!,
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .padding(start = 10.dp, end = 10.dp, top = 3.dp)
                    .constrainAs(detailsText) {
                        start.linkTo(userImage.end)
                        top.linkTo(userText.bottom)
                    },
                color = Color.Black,
                fontSize = 10.sp,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
            Text(
                text = githubUser.url!!,
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .padding(start = 10.dp, end = 10.dp, top = 3.dp)
                    .constrainAs(detailUser2) {
                        start.linkTo(userImage.end)
                        top.linkTo(detailsText.bottom)
                    },
                color = Color.Black,
                fontSize = 10.sp,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
            Text(
                text = githubUser.followers_url!!,
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .padding(start = 10.dp, end = 10.dp, top = 3.dp)
                    .constrainAs(detailUser3) {
                        start.linkTo(userImage.end)
                        top.linkTo(detailUser2.bottom)
                    },
                color = Color.Black,
                fontSize = 10.sp,
                maxLines = 2,
                overflow = TextOverflow.Clip
            )

            for (i in localUsersList.indices) {
                if (( localUsersList[i].note!!.isNotEmpty()) && localUsersList[i].login == githubUser.login) {
                    Surface(
                        color = Color.Black.copy(alpha = 0f),
                        modifier = Modifier
                            .wrapContentSize()
                            .constrainAs(note) {
                                start.linkTo(userText.end)
                                end.linkTo(endLine)
                                top.linkTo(userText.top)
                            }
                    ) {
                        Icon(
                            tint = colorResource(id = R.color.brown_design),
                            imageVector = Icons.Default.Note,
                            contentDescription = "Note",
                            modifier = Modifier
                                .size(20.dp)

                        )
                    }
                }
            }
        }
    }
}