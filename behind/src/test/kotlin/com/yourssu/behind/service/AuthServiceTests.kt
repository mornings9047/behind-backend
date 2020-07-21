package com.yourssu.behind.service

import com.yourssu.behind.model.dto.user.request.UserSignUpRequestDto
import com.yourssu.behind.repository.user.UserRepository
import com.yourssu.behind.service.auth.AuthService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AuthServiceTests {
    @Autowired
    lateinit var authService: AuthService

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun isSchoolValidTest() {
        Assertions.assertTrue(authService.isSchoolIdValid("20170000"))
        Assertions.assertTrue(authService.isSchoolIdValid("20121092"))

        Assertions.assertFalse(authService.isSchoolIdValid("21010001"))
        Assertions.assertFalse(authService.isSchoolIdValid("12345678"))
        Assertions.assertFalse(authService.isSchoolIdValid("2017"))
        Assertions.assertFalse(authService.isSchoolIdValid("201705030"))

        Assertions.assertFalse(authService.isSchoolIdValid("2012a092"))
        Assertions.assertFalse(authService.isSchoolIdValid("string"))
    }

    @Test
    fun isUsernameValidTest() {
        Assertions.assertTrue(authService.isUsernameValid("VALID_ID"))
        Assertions.assertTrue(authService.isUsernameValid("valid_id"))
        Assertions.assertTrue(authService.isUsernameValid("@valid_Id@"))

        Assertions.assertFalse(authService.isUsernameValid("INVALID"))
        Assertions.assertFalse(authService.isUsernameValid("invalid"))
        Assertions.assertFalse(authService.isUsernameValid("!@#$%^&"))
        Assertions.assertFalse(authService.isUsernameValid("NUM_123"))
        Assertions.assertFalse(authService.isUsernameValid("@TOO_LONG_USERNAME@"))
    }

    @Test
    fun isPasswordValidTest() {
        Assertions.assertTrue(authService.isPasswordValid("MinLength1"))
        Assertions.assertTrue(authService.isPasswordValid("MaxLength12345678900"))
        Assertions.assertTrue(authService.isPasswordValid("ValidPassword123"))

        Assertions.assertFalse(authService.isPasswordValid("Short00"))
        Assertions.assertFalse(authService.isPasswordValid("TooLongPassword12345678"))
        Assertions.assertFalse(authService.isPasswordValid("OnlyString"))
        Assertions.assertFalse(authService.isPasswordValid("NOSMALL123"))
        Assertions.assertFalse(authService.isPasswordValid("nolarge123"))
        Assertions.assertFalse(authService.isPasswordValid("12345678"))
    }

    @Test
    fun signUpTest() {
        val user = UserSignUpRequestDto("20170503", "user_name", "Password123")
        val before = userRepository.findAll().size
        authService.signUp(user)
        val after = userRepository.findAll().size
        Assertions.assertEquals(before + 1, after)
    }
}
