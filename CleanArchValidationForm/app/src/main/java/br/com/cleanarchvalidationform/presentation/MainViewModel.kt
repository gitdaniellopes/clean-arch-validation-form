package br.com.cleanarchvalidationform.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cleanarchvalidationform.domain.usecase.ValidateEmailUseCase
import br.com.cleanarchvalidationform.domain.usecase.ValidatePasswordUseCase
import br.com.cleanarchvalidationform.domain.usecase.ValidateRepeatedPasswordUseCase
import br.com.cleanarchvalidationform.domain.usecase.ValidateTermsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase = ValidateEmailUseCase(),
    private val validatePasswordUseCase: ValidatePasswordUseCase = ValidatePasswordUseCase(),
    private val validateRepeatedPasswordUseCase: ValidateRepeatedPasswordUseCase = ValidateRepeatedPasswordUseCase(),
    private val validateTermsUseCase: ValidateTermsUseCase = ValidateTermsUseCase()
) : ViewModel() {

    var state by mutableStateOf(RegistrationFormState())


    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                state = state.copy(repeatedPassword = event.repeatedPassword)
            }
            is RegistrationFormEvent.AcceptTermsChanged -> {
                state = state.copy(acceptedTerms = event.isAccepted)
            }
            is RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmailUseCase.execute(email = state.email)
        val passwordResult = validatePasswordUseCase.execute(password = state.password)
        val repeatedPasswordResult = validateRepeatedPasswordUseCase.execute(
            password = state.password,
            repeatedPassword = state.repeatedPassword
        )
        val acceptedResult = validateTermsUseCase.execute(acceptedTerms = state.acceptedTerms)

        //verificamos se existe um erro ou se foi bem succedido.
        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult,
            acceptedResult
        ).any { !it.successful }

        state = state.copy(
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage,
            repeatedPasswordError = repeatedPasswordResult.errorMessage,
            termsError = acceptedResult.errorMessage
        )
        if (hasError) {
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}