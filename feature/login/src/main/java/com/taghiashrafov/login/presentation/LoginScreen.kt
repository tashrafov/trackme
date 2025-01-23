package com.taghiashrafov.login.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.taghiashrafov.components.IconButton
import com.taghiashrafov.components.OutlinedInput
import com.taghiashrafov.login.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    SnackbarHost(hostState = snackbarHostState)

    LaunchedEffect(key1 = true) {
        viewModel.sideEffects.collectLatest { sideEffect ->
            when (sideEffect) {
                is LoginSideEffect.ShowMessage -> {
                    Log.d("LoginScreen", "ShowMessage: ${sideEffect.message}")
                    scope.launch { snackbarHostState.showSnackbar(sideEffect.message) }
                }

                is LoginSideEffect.NavigateToMain -> {
                    //todo navigate to main
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(320.dp)
                        .align(Alignment.CenterHorizontally)
                )

                OutlinedInput(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                    label = "Email",
                    value = state.value.email,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    trailingIcon = {
                        IconButton(
                            onClick = {}, icon = Icons.Default.Email, contentDescription = "Email"
                        )
                    }) { viewModel.onEvent(LoginEvents.OnEmailChanged(it)) }

                OutlinedInput(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp),
                    label = "Password",
                    value = state.value.password,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                    visualTransformation = if (state.value.passwordVisibilityState) PasswordVisualTransformation() else VisualTransformation.None,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                viewModel.onEvent(LoginEvents.OnPasswordVisibilityChangeClicked)
                            },
                            icon = if (state.value.passwordVisibilityState) painterResource(R.drawable.outline_visibility_24) else painterResource(
                                R.drawable.outline_visibility_off_24
                            ),
                            contentDescription = "Email"
                        )
                    }) {
                    viewModel.onEvent(LoginEvents.OnPasswordChanged(it))
                }

                Button(onClick = {
                    viewModel.onEvent(LoginEvents.OnLoginClicked)
                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp),
                    content = {
                        Text(
                            "Login"
                        )
                    })

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(modifier = Modifier
                        .padding(8.dp)
                        .background(
                            MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp)
                        ), onClick = {
                        viewModel.onEvent(LoginEvents.OnLoginWithGoogleClicked)
                    }) {
                        Image(
                            painter = painterResource(R.drawable.google_logo),
                            contentDescription = "Google"
                        )
                    }
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp)
                    .clickable {
                        viewModel.onEvent(LoginEvents.OnRegisterClicked)
                    },
                text = buildAnnotatedString {
                    append("Don't have account yet? ")
                    withStyle(
                        style = MaterialTheme.typography.labelLarge.toSpanStyle()
                    ) {
                        append("Register")
                    }
                },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}