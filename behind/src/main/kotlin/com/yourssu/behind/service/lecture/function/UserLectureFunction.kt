package com.yourssu.behind.service.lecture.function

import com.yourssu.behind.exception.lecture.LectureAlreadyExistsException
import com.yourssu.behind.exception.lecture.LectureNotExistsException
import com.yourssu.behind.model.dto.lecture.ReturnLectureDto
import com.yourssu.behind.repository.lecture.LectureRepository
import com.yourssu.behind.service.auth.JwtService

class UserLectureFunction(private val jwtService : JwtService, private val lectureRepository: LectureRepository ){

    fun addUserLecture(lectureId: Long){
        val user = jwtService.getUser()
        val lecture = lectureRepository.findById(lectureId).orElseThrow { LectureNotExistsException() }
        for(i in user.lectures){
            if(i == lecture)
                throw LectureAlreadyExistsException()
        }
        user.lectures.add(lecture)
    }

    fun removeUserLecture(lectureId: Long){
        val user = jwtService.getUser()
        val lecture = lectureRepository.findById(lectureId).orElseThrow { LectureNotExistsException() }
        user.lectures.removeIf { i -> i == lecture }
    }

    fun getUserLectures() : Collection<ReturnLectureDto>{
        val user = jwtService.getUser()
        val result : MutableList<ReturnLectureDto> = mutableListOf()

        for(i in 0 until user.lectures.size)
            result.add(ReturnLectureDto(user.lectures[i]))

        return result
    }
}