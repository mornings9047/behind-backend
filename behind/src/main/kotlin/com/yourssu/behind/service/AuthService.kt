package com.yourssu.behind.service

import com.yourssu.behind.exception.InvalidPasswordException
import com.yourssu.behind.exception.InvalidSchoolIdException
import com.yourssu.behind.exception.InvalidUsernameException
import com.yourssu.behind.exception.UserAlreadyExistsException
import com.yourssu.behind.model.dto.UserSignUpRequestDto
import com.yourssu.behind.model.entity.user.User
import com.yourssu.behind.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthService {
    @Autowired
    lateinit var userRepository: UserRepository

    fun isUserSignUpRequestDtoValid(userSignUpRequestDto: UserSignUpRequestDto): Boolean {
        if (!isSchoolIdValid(userSignUpRequestDto.schoolId))
            throw InvalidSchoolIdException("유효하지 않은 학번입니다")
        if (!isUsernameValid(userSignUpRequestDto.username))
            throw InvalidUsernameException("유효하지 않은 아이디입니다")
        if (!isPasswordValid(userSignUpRequestDto.password))
            throw InvalidPasswordException("유효하지 않은 비밀번호입니다")
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
            if (userRepository.existsBySchoolId(userSignUpRequestDto.schoolId))
                throw UserAlreadyExistsException("이미 존재하는 학번입니다.")

        userRepository.save(User(
                schoolId = userSignUpRequestDto.schoolId,
                userName = userSignUpRequestDto.username,
                password = userSignUpRequestDto.password
        ))
    }

}
