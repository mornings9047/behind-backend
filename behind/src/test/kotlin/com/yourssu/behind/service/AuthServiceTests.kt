package com.yourssu.behind.service

import com.yourssu.behind.exception.user.PasswordNotMatchedException
import com.yourssu.behind.exception.user.UserNotExistsException
import com.yourssu.behind.model.dto.UserSignInRequestDto
import com.yourssu.behind.model.dto.UserSignUpRequestDto
import com.yourssu.behind.repository.user.UserRepository
import com.yourssu.behind.service.auth.AuthService
import com.yourssu.behind.service.auth.function.AuthValidFunction
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
    final lateinit var userRepository: UserRepository

    val authValidFunction = AuthValidFunction(userRepository)

    @Test
    fun isSchoolValidTest() {
        Assertions.assertTrue(authValidFunction.isSchoolIdValid("20170000"))
        Assertions.assertTrue(authValidFunction.isSchoolIdValid("20121092"))

        Assertions.assertFalse(authValidFunction.isSchoolIdValid("21010001"))
        Assertions.assertFalse(authValidFunction.isSchoolIdValid("12345678"))
        Assertions.assertFalse(authValidFunction.isSchoolIdValid("2017"))
        Assertions.assertFalse(authValidFunction.isSchoolIdValid("201705030"))

        Assertions.assertFalse(authValidFunction.isSchoolIdValid("2012a092"))
        Assertions.assertFalse(authValidFunction.isSchoolIdValid("string"))
    }

    @Test
    fun isPasswordValidTest() {
        Assertions.assertTrue(authValidFunction.isPasswordValid("MinLength1"))
        Assertions.assertTrue(authValidFunction.isPasswordValid("MaxLength12345678900"))
        Assertions.assertTrue(authValidFunction.isPasswordValid("ValidPassword123"))

        Assertions.assertFalse(authValidFunction.isPasswordValid("Short00"))
        Assertions.assertFalse(authValidFunction.isPasswordValid("TooLongPassword12345678"))
        Assertions.assertFalse(authValidFunction.isPasswordValid("OnlyString"))
        Assertions.assertFalse(authValidFunction.isPasswordValid("NOSMALL123"))
        Assertions.assertFalse(authValidFunction.isPasswordValid("nolarge123"))
        Assertions.assertFalse(authValidFunction.isPasswordValid("12345678"))
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
