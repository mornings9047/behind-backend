package com.yourssu.behind.service.lecture

import com.yourssu.behind.model.dto.lecture.ReturnLectureDto
import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.lecture.SearchType
import com.yourssu.behind.repository.lecture.LectureRepository
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.professor.ProfessorRepository
import com.yourssu.behind.service.auth.JwtService
import com.yourssu.behind.service.lecture.function.FindLectureFunction
import com.yourssu.behind.service.lecture.function.UserLectureFunction
import com.yourssu.behind.service.post.function.GetNewPostFeedFunction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LectureService @Autowired constructor(lectureRepository: LectureRepository,
                                            private val jwtService: JwtService,
                                            professorRepository: ProfessorRepository,
                                            postRepository: PostRepository){

    private val findLectureFunction = FindLectureFunction(lectureRepository, professorRepository)
    private val userLectureFunction = UserLectureFunction(jwtService, lectureRepository)
    private val getNewPostFeedFunction = GetNewPostFeedFunction(jwtService, postRepository)


    @Transactional
    fun searchLecture(keyword: String, type: SearchType): Collection<ReturnLectureDto> {
        return findLectureFunction.searchLectureByKeyword(keyword, type)
    }

    @Transactional
    fun addUserLecture(lectureId: Long) {
        userLectureFunction.addUserLecture(lectureId)
    }

    @Transactional
    fun deleteUserLecture(lectureId: Long) {
        userLectureFunction.removeUserLecture(lectureId)
    }

    @Transactional
    fun getAllUserLecture(): Collection<ReturnLectureDto> {
        return userLectureFunction.getUserLectures()
    }

    @Transactional
    fun newPostFeed(page: Int): Collection<ResponsePostsDto> {
        return getNewPostFeedFunction.getNewPostFeed(page)
    }
}