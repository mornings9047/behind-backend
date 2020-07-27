package com.yourssu.behind.service.authTest

import com.yourssu.behind.exception.user.PasswordNotMatchedException
import com.yourssu.behind.exception.user.UserNotExistsException
import com.yourssu.behind.model.dto.user.request.UserSignInRequestDto
import com.yourssu.behind.model.dto.user.request.UserSignUpRequestDto
import com.yourssu.behind.repository.user.UserRepository
import com.yourssu.behind.service.auth.AuthService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.event.annotation.AfterTestExecution
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class AuthServiceTests {

    @Autowired
    lateinit var authService: AuthService

    @Autowired
    final lateinit var userRepository: UserRepository

    @AfterTestExecution
    @Test
    fun deleteAll() {
        userRepository.deleteAll()
    }

//    @Test
//    fun isSchoolValidTest() {
//        assertTrue(authService.authValidFunction.isSchoolIdValid("20170000"))
//        assertTrue(authService.authValidFunction.isSchoolIdValid("20121092"))
//
//        Assertions.assertFalse(authService.authValidFunction.isSchoolIdValid("21010001"))
//        Assertions.assertFalse(authService.authValidFunction.isSchoolIdValid("12345678"))
//        Assertions.assertFalse(authService.authValidFunction.isSchoolIdValid("2017"))
//        Assertions.assertFalse(authService.authValidFunction.isSchoolIdValid("201705030"))
//
//        Assertions.assertFalse(authService.authValidFunction.isSchoolIdValid("2012a092"))
//        Assertions.assertFalse(authService.authValidFunction.isSchoolIdValid("string"))
//    }

//    @Test
//    fun isPasswordValidTest() {
//        assertTrue(authService.authValidFunction.isPasswordValid("MinLength1"))
//        assertTrue(authService.authValidFunction.isPasswordValid("MaxLength12345678900"))
//        assertTrue(authService.authValidFunction.isPasswordValid("ValidPassword123"))
//
//        Assertions.assertFalse(authService.authValidFunction.isPasswordValid("Short00"))
//        Assertions.assertFalse(authService.authValidFunction.isPasswordValid("TooLongPassword12345678"))
//        Assertions.assertFalse(authService.authValidFunction.isPasswordValid("OnlyString"))
//        Assertions.assertFalse(authService.authValidFunction.isPasswordValid("NOSMALL123"))
//        Assertions.assertFalse(authService.authValidFunction.isPasswordValid("nolarge123"))
//        Assertions.assertFalse(authService.authValidFunction.isPasswordValid("12345678"))
//    }

    @Test
    fun signUpTest() {
        val user = UserSignUpRequestDto("20171111", "Password123")
        val before = userRepository.findAll().size
        authService.signUp(user)
        val after = userRepository.findAll().size
        Assertions.assertEquals(before + 1, after)
    }

    @Test
    fun signInTest() {
        val user = UserSignUpRequestDto("20170000", "Password123")
        val incorrectId = UserSignInRequestDto("20170001", "Password123")
        val incorrectPassword = UserSignInRequestDto("20170000", "Password1234")
        authService.signUp(user)

        assertThrows<UserNotExistsException> { authService.signIn(incorrectId) }
        assertThrows<PasswordNotMatchedException> { authService.signIn(incorrectPassword) }
        assertTrue(authService.signIn(UserSignInRequestDto(user.schoolId, user.password)))
    }
}
