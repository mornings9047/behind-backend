package com.yourssu.behind.service.lecture.function

import com.yourssu.behind.model.dto.lecture.ReturnLectureDto
import com.yourssu.behind.model.entity.lecture.SearchType
import com.yourssu.behind.repository.lecture.LectureRepository
import com.yourssu.behind.repository.professor.ProfessorRepository

class FindLectureFunction(private val lectureRepository: LectureRepository, private val professorRepository: ProfessorRepository) {
    fun searchLectureByKeyword(keyword: String, type: SearchType): Collection<ReturnLectureDto> {
        val result: MutableList<ReturnLectureDto> = mutableListOf()
        when (type) {
            SearchType.Lecture -> {
                val lectures = lectureRepository.findByCourseNameContains(keyword)
                for (lecture in lectures)
                    result.add(ReturnLectureDto(lecture))
            }
            SearchType.Professor -> {
                val professors = professorRepository.findByNameContains(keyword)
                val lectures = lectureRepository.findAll()
                for (professor in professors) {
                    for (lecture in lectures) {
                        if (lecture.professor.name == professor.name)
                            result.add(ReturnLectureDto(lecture))
                    }
                }
            }
        }
        return result

    }
}
