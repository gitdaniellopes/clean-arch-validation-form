package br.com.cleanarchvalidationform.domain.usecase

class ValidatePasswordUseCase {

    fun execute(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "A senha precisa ter no minimo 8 caracteres."
            )
        }
        val containsLettersAndDigits =
            password.any { it.isDigit() } && password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "A senha precisa contar letras e digitos."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}