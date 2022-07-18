package br.com.cleanarchvalidationform.domain.usecase

class ValidateTermsUseCase {

    fun execute(acceptedTerms: Boolean): ValidationResult {
        if (!acceptedTerms) {
            return ValidationResult(
                successful = false,
                errorMessage = "Aceite os termos."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}