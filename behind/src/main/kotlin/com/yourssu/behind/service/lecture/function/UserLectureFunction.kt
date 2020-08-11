package com.yourssu.behind.service.lecture.function

import com.yourssu.behind.exception.lecture.AlreadyLectureException
import com.yourssu.behind.exception.lecture.LectureNotExistsException
import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.user.User
import com.yourssu.behind.repository.lecture.LectureRepository
import com.yourssu.behind.service.auth.JwtService

class UserLectureFunction(private val jwtService : JwtService, private val lectureRepository: LectureRepository ){

    fun addUserLecture(lectureId: Long){
        val user = jwtService.getUser()
        val lecture = lectureRepository.findById(lectureId).orElseThrow { LectureNotExistsException() }
        for(i in user.lectures){
            if(i == lecture)
                throw AlreadyLectureException()
        }
        user.lectures.add(lecture)
    }

    fun removeUserLecture(lectureId: Long){
        val user = jwtService.getUser()
        val lecture = lectureRepository.findById(lectureId).orElseThrow { LectureNotExistsException() }
        user.lectures.removeIf { i -> i == lecture }
    }
}