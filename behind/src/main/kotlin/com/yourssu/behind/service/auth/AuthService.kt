package com.yourssu.behind.service.auth

import com.yourssu.behind.exception.user.InvalidPasswordException
import com.yourssu.behind.exception.user.InvalidSchoolIdException
import com.yourssu.behind.exception.user.UserAlreadyExistsException
import com.yourssu.behind.model.dto.user.request.UserSignUpRequestDto
import com.yourssu.behind.model.entity.user.User
import com.yourssu.behind.repository.user.UserRepository
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthService @Autowired constructor(val userRepository: UserRepository) {

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

    fun isUsernameValid(username: String): Boolean {
        val regex = Regex("^(?=.*[A-Za-z])(?=.*[@\$!%*_#?&])[A-Za-z@$!%*_#?&]{1,16}$")
        return regex.matches(username)
    }

    fun isPasswordValid(password: String): Boolean {
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$")
        return regex.matches(password)
    }

    fun signUp(userSignUpRequestDto: UserSignUpRequestDto) {

        if (isUserSignUpRequestDtoValid(userSignUpRequestDto))
            userRepository.save(User(
                    schoolId = userSignUpRequestDto.schoolId,
                    password = BCrypt.hashpw(userSignUpRequestDto.password, BCrypt.gensalt())
            ))
    }
}
