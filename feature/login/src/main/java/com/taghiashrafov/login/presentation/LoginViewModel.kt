package com.taghiashrafov.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState = _uiState.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            LoginUIState()
        )

    private val _sideEffects = Channel<LoginSideEffect>()
    val sideEffects = _sideEffects.receiveAsFlow()

    fun onEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.OnEmailChanged -> {
                _uiState.update { it.copy(email = event.email) }
            }

            LoginEvents.OnLoginClicked -> {
                onLoginClicked()
            }

            LoginEvents.OnLoginWithGoogleClicked -> {
                //todo login with google
            }

            is LoginEvents.OnPasswordChanged -> {
                _uiState.update { it.copy(password = event.password) }
            }

            LoginEvents.OnPasswordVisibilityChangeClicked -> {
                _uiState.update { it.copy(passwordVisibilityState = !uiState.value.passwordVisibilityState) }
            }

            LoginEvents.OnRegisterClicked -> {
                //todo navigate to register
            }
        }
    }

    private fun onLoginClicked() {
        val email = uiState.value.email
        val password = uiState.value.password
        //todo change to api call
        if (email.isNotBlank() && password.isNotBlank()) {
            _sideEffects.trySend(LoginSideEffect.NavigateToMain("home"))
        } else {
            _sideEffects.trySend(LoginSideEffect.ShowMessage("Email or password is empty"))
        }
    }
}