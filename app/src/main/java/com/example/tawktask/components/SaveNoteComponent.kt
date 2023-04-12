package com.example.tawktask.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tawktask.R
import com.example.tawktask.network.model.GithubUser
import com.example.tawktask.theme.ModifiedDesign
import com.example.tawktask.theme.PrimaryDesignDark
import com.example.tawktask.theme.White

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SaveNoteComponent(
    noteValue: MutableState<String>,
    data: GithubUser,
    onSaveNoteClicked: (GithubUser) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(noteValue.value) {
        noteValue.value.trim().isNotEmpty()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(0),
            elevation = 5.dp ,
            backgroundColor = if (isSystemInDarkTheme())  Color.White
            else Color.White
        ) {
            CommonTextField(
                valueState = noteValue,
                placeholder = stringResource(id = R.string.add_note),
                onAction = KeyboardActions {
                    if (!valid) return@KeyboardActions
                    noteValue.value = ""
                    keyboardController?.hide()
                })
        }
        SaveButton(modifier = Modifier) {
            data.note = noteValue.value
            onSaveNoteClicked.invoke(data)
        }
    }
}

