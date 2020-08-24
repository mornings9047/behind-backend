package com.yourssu.behind.service.lecture.function

import com.yourssu.behind.model.dto.lecture.ReturnLectureDto
import com.yourssu.behind.model.entity.lecture.SearchType
import com.yourssu.behind.repository.lecture.LectureRepository
import com.yourssu.behind.repository.professor.ProfessorRepository

class FindLectureFunction(private val lectureRepository: LectureRepository,
                          private val professorRepository: ProfessorRepository) {
    fun searchLectureByKeyword(keyword: String, type: SearchType): Collection<ReturnLectureDto> {
        val lectures: MutableList<ReturnLectureDto> = mutableListOf()
        when (type) {
            SearchType.Lecture -> {
                for (lecture in lectureRepository.findByCourseNameContains(keyword))
                    lectures.add(ReturnLectureDto(lecture))
            }
            SearchType.Professor -> {
                val professors = professorRepository.findByNameContains(keyword)
                for (professor in professors)
                    for (lecture in lectureRepository.findAllByProfessorId(professor.id))
                        lectures.add(ReturnLectureDto(lecture))
            }
        }
        return lectures
    }
}

