package com.yourssu.behind.service.auth.function

import com.yourssu.behind.exception.user.InvalidPasswordException
import com.yourssu.behind.exception.user.InvalidSchoolIdException
import com.yourssu.behind.exception.user.UserAlreadyExistsException
import com.yourssu.behind.model.dto.user.request.UserSignUpRequestDto
import com.yourssu.behind.repository.user.UserRepository

class AuthValidFunction(private val userRepository: UserRepository) {
    fun isUserSignUpRequestDtoValid(userSignUpRequestDto: UserSignUpRequestDto): Boolean {
        if (!isSchoolIdValid(userSignUpRequestDto.schoolId))
            throw InvalidSchoolIdException()
        if (userRepository.existsBySchoolId(userSignUpRequestDto.schoolId))
            throw UserAlreadyExistsException()
        if (!isPasswordValid(userSignUpRequestDto.password))
            throw InvalidPasswordException()
        return true
    }

    fun isSchoolIdValid(schoolId: String): Boolean {
        val regex = Regex("^(20)([0-9]){6}$")
        return regex.matches(schoolId)
    }

    private fun isPasswordValid(password: String): Boolean {
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$")
        return regex.matches(password)
    }
}
