package com.example.tawktask.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.example.tawktask.R
import com.example.tawktask.theme.ModifiedDesign
import com.example.tawktask.theme.PrimaryDesignDark

@Composable
fun InputSearchField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {

    val context = LocalContext.current
    val ernestineFont = remember {
        FontFamily(
            typeface = ResourcesCompat.getFont(context, R.font.ernestine_font)!!
        )
    }

    TextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = labelId, fontFamily = ernestineFont) },
        singleLine = isSingleLine,
        maxLines = 1,
        shape = RoundedCornerShape(8.dp),
        textStyle = TextStyle(
            fontSize = 14.sp,
            color = Color.Black, fontFamily = ernestineFont
        ),
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedLabelColor = if (isSystemInDarkTheme()) Color.Black
            else ModifiedDesign,
            cursorColor = if (isSystemInDarkTheme()) Color.Black
            else ModifiedDesign,
            focusedBorderColor = colorResource(id = R.color.brown_modified),
            unfocusedBorderColor = Color.Transparent,
            focusedLabelColor = colorResource(id = R.color.brown_modified),
            backgroundColor = Color.White
        ),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction
    )
}

@Composable
fun CommonTextField(
    valueState: MutableState<String>,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = placeholder) },
        maxLines = 5,
        singleLine = false,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp, top = 10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedLabelColor = if (isSystemInDarkTheme()) Color.Black
            else ModifiedDesign,
            cursorColor = if (isSystemInDarkTheme()) Color.Black
            else ModifiedDesign,
            focusedBorderColor = colorResource(id = R.color.brown_modified),
            unfocusedBorderColor = if (isSystemInDarkTheme()) Color.Black
            else ModifiedDesign,
            focusedLabelColor = if (isSystemInDarkTheme()) Color.Black
            else Color.Black,
            backgroundColor = Color.White,
            textColor = Color.Black
        ),
    )
}


