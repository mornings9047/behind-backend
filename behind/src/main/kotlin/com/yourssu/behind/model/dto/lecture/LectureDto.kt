package com.yourssu.behind.model.dto.lecture

import com.yourssu.behind.model.entity.lecture.LectureSemester
import com.yourssu.behind.model.entity.professor.Professor

class LectureDto(val courseName: String, val major: String, val professor: Professor, val year: String, val semester: LectureSemester)
