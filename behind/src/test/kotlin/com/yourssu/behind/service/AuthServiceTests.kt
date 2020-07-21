package com.yourssu.behind.service

import com.yourssu.behind.exception.PasswordNotMatchedException
import com.yourssu.behind.exception.UserNotExistsException
import com.yourssu.behind.model.dto.UserSignInRequestDto
import com.yourssu.behind.model.dto.UserSignUpRequestDto
import com.yourssu.behind.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
        val user = UserSignUpRequestDto("20170503", "Password123")
        val before = userRepository.findAll().size
        authService.signUp(user)
        val after = userRepository.findAll().size
        Assertions.assertEquals(before + 1, after)
    }

    @Test
    fun signInTest() {
        val user = UserSignUpRequestDto("20170503", "Password123")
        val incorrectId = UserSignInRequestDto("20170501", "Password123")
        val incorrectPassword = UserSignInRequestDto("20170503", "Password1234")
        authService.signUp(user)

        assertThrows<UserNotExistsException> { authService.signIn(incorrectId) }
        assertThrows<PasswordNotMatchedException> { authService.signIn(incorrectPassword) }
        Assertions.assertTrue(authService.signIn(UserSignInRequestDto(user.schoolId, user.password)))
    }
}
