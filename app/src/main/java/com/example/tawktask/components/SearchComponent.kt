package com.example.tawktask.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.tawktask.R
import com.example.tawktask.theme.PrimaryDesign
import com.example.tawktask.theme.ModifiedDesign
import com.example.tawktask.theme.PrimaryDesignDark


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchComponent(onSearchClicked: (String) -> Unit) {
    val searchValue = rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchValue.value) {
        searchValue.value.trim().isNotEmpty()
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (searchCard) = createRefs()

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .constrainAs(searchCard) {
                    top.linkTo(parent.top)
                },

            shape = RoundedCornerShape(0),
            elevation = 0.dp,
            backgroundColor = if (isSystemInDarkTheme())  PrimaryDesignDark
            else ModifiedDesign
        ) {

            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

                val (searchField, searchIconImage) = createRefs()

                InputSearchField(
                    Modifier
                        .fillMaxWidth(.9f)
                        .wrapContentHeight()
                        .padding(start = 15.dp, top = 8.dp)
                        .constrainAs(searchField) {
                            top.linkTo(searchCard.top)
                            bottom.linkTo(searchCard.bottom)
                            start.linkTo(searchCard.start)
                        },
                    searchValue,
                    stringResource(id = R.string.search),
                    true,
                    true,
                    onAction = KeyboardActions {
                        if (!valid) return@KeyboardActions
                        searchValue.value = ""
                        keyboardController?.hide()
                    })

                IconButton(
                    onClick = {
                        if (searchValue.value.isEmpty()) {
                            searchValue.value = "empty"
                        }
                        onSearchClicked.invoke(searchValue.value)
                    },
                    modifier = Modifier
                        .padding(end = 10.dp, start = 50.dp)
                        .constrainAs(searchIconImage) {
                            top.linkTo(searchCard.top)
                            bottom.linkTo(searchCard.bottom)
                            end.linkTo(searchCard.end)
                            start.linkTo(searchField.end)
                        }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                    )
                }
            }
        }
    }
}


