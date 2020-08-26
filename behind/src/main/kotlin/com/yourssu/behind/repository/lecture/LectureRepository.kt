package com.yourssu.behind.repository.lecture

import com.yourssu.behind.model.entity.lecture.Lecture
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LectureRepository : JpaRepository<Lecture, Long> {
    fun findByCourseNameContains(courseName: String): List<Lecture>
    fun findAllByProfessorId(id: Long?): List<Lecture>
    fun existsByLectureCode(lectureCode: String): Boolean
    override fun findById(lectureId: Long): Optional<Lecture>
}
