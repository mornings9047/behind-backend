package com.yourssu.behind.service.auth

import com.yourssu.behind.exception.user.PasswordNotMatchedException
import com.yourssu.behind.exception.user.UserNotExistsException
import com.yourssu.behind.model.dto.user.request.UserSignInRequestDto
import com.yourssu.behind.model.dto.user.request.UserSignUpRequestDto
import com.yourssu.behind.model.entity.user.User
import com.yourssu.behind.repository.user.UserRepository
import com.yourssu.behind.service.auth.function.AuthValidFunction
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthService @Autowired constructor(private val userRepository: UserRepository) {
    val authValidFunction = AuthValidFunction(userRepository)

    fun signUp(userSignUpRequestDto: UserSignUpRequestDto) {
        if (authValidFunction.isUserSignUpRequestDtoValid(userSignUpRequestDto))
            userRepository.save(User(
                    schoolId = userSignUpRequestDto.schoolId,
                    password = BCrypt.hashpw(userSignUpRequestDto.password, BCrypt.gensalt())
            ))
    }

    fun signIn(userSignInRequestDto: UserSignInRequestDto): Boolean {
        if (!userRepository.existsBySchoolId(userSignInRequestDto.schoolId))
            throw UserNotExistsException()
        if (!BCrypt.checkpw(userSignInRequestDto.password, userRepository.findBySchoolId(userSignInRequestDto.schoolId).get().password))
            throw PasswordNotMatchedException()
        return true
    }
}
