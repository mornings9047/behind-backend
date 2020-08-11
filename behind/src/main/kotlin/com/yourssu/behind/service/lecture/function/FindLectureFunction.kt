package com.yourssu.behind.service.lecture.function

import com.yourssu.behind.model.dto.lecture.ReturnLectureDto
import com.yourssu.behind.model.entity.lecture.SearchType
import com.yourssu.behind.repository.lecture.LectureRepository
import com.yourssu.behind.repository.professor.ProfessorRepository
import java.util.ArrayList

class FindLectureFunction(private val lectureRepository: LectureRepository, private val professorRepository: ProfessorRepository){
    fun searchLectureByKeyword(keyword: String, type: SearchType) : Collection<ReturnLectureDto> {
        when (type) {
            SearchType.Lecture -> {
                val lectureList = lectureRepository.findByCourseNameContains(keyword)
                var result: ArrayList<ReturnLectureDto> = arrayListOf()

                for (i in lectureList.indices) {
                    val returnLectureDto = ReturnLectureDto(lectureList.get(index = i))
                    result.add(returnLectureDto)
                }
                return result
            }

            SearchType.Professor -> {
                val professorList = professorRepository.findByNameContains(keyword)
                var result: ArrayList<ReturnLectureDto> = arrayListOf()
                val lectures = lectureRepository.findAll()
                for (i in professorList.indices) {
                    for (j in 0 until lectures.size) {
                        if (lectures[j].professor.name == professorList[i].name) {
                            val returnLectureDto = ReturnLectureDto(lectures[j])
                            result.add(returnLectureDto)
                        }
                    }
                }
                return result
            }
        }
    }
}
