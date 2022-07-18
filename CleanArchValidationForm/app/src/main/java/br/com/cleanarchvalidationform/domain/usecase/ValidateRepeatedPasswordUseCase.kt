package br.com.cleanarchvalidationform.domain.usecase

class ValidateRepeatedPasswordUseCase {

    fun execute(password: String, repeatedPassword: String): ValidationResult {
        if (password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "As senhas não combinam."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}