package com.yourssu.behind.repository.lecture

import com.yourssu.behind.model.entity.lecture.Lecture
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LectureRepository : JpaRepository<Lecture, Long>
