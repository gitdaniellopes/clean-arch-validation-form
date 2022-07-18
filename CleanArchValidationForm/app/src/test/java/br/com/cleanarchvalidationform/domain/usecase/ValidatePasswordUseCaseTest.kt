package br.com.cleanarchvalidationform.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ValidatePasswordUseCaseTest {

    private lateinit var validatePassword: ValidatePasswordUseCase

    @Before
    fun setUp() {
        validatePassword = ValidatePasswordUseCase()
    }

    @Test
    fun `Password is letter-only, returns error`() {
        val result = validatePassword.execute("abcdefgh")
        assertEquals(result.successful, false)
    }
}