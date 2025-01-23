package com.taghiashrafov.login.presentation

import androidx.compose.runtime.Immutable

@Immutable
sealed interface LoginSideEffect {
    data class ShowMessage(val message: String) : LoginSideEffect
    data class NavigateToMain(val destination: String) : LoginSideEffect
}

@Immutable
sealed interface LoginEvents {
    data class OnEmailChanged(val email: String) : LoginEvents
    data class OnPasswordChanged(val password: String) : LoginEvents
    data object OnPasswordVisibilityChangeClicked : LoginEvents
    data object OnLoginClicked : LoginEvents
    data object OnLoginWithGoogleClicked : LoginEvents
    data object OnRegisterClicked : LoginEvents
}

@Immutable
data class LoginUIState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val passwordVisibilityState: Boolean = true
)