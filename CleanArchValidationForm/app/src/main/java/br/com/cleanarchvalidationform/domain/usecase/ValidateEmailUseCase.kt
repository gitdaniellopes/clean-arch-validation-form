package br.com.cleanarchvalidationform.domain.usecase

import android.util.Patterns

class ValidateEmailUseCase {

    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Email em branco."
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Email invalido"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}