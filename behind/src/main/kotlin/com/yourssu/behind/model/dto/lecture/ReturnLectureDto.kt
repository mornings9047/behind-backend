package com.yourssu.behind.model.dto.lecture

import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.lecture.LectureSemester

class ReturnLectureDto(lecture: Lecture) {
    val id: Long? = lecture.id
    val major: String = lecture.major
    val year: String = lecture.year
    val semester: LectureSemester = lecture.semester
    val courseName = lecture.courseName
    val professor = lecture.professor.name
}