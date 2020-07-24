package com.yourssu.behind.service

import com.yourssu.behind.model.dto.post.request.CreateOrUpdateRequestPostDto
import com.yourssu.behind.model.dto.user.request.UserSignUpRequestDto
import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.lecture.LectureSemester
import com.yourssu.behind.model.entity.post.PostType
import com.yourssu.behind.model.entity.professor.Professor
import com.yourssu.behind.repository.lecture.LectureRepository
import com.yourssu.behind.repository.professor.ProfessorRepository
import com.yourssu.behind.service.auth.AuthService
import com.yourssu.behind.service.post.PostService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.event.annotation.BeforeTestExecution

@SpringBootTest
class PostServiceTests {
    @Autowired
    lateinit var postService: PostService

    @Autowired
    lateinit var authService: AuthService

    @Autowired
    lateinit var professorRepository: ProfessorRepository

    @Autowired
    lateinit var lectureRepository: LectureRepository

    @BeforeTestExecution
    @Test
    fun createPosts() {
        authService.signUp(UserSignUpRequestDto("20170000", "Password123"))

        val professor = Professor(name = "JOC")
        professorRepository.save(professor)
        lectureRepository.save(Lecture(major = "computer", year = "20", semester = LectureSemester.SPRING, courseName = "OOP", professor = professor))

        for (i in 1..20)
            postService.createPost(CreateOrUpdateRequestPostDto(schoolId = "20170000", title = "title$i", type = PostType.FREE, content = "content$i", lectureId = 3), imgFile = null)
    }

    @Test
    fun searchPostsTest() {
        for (responsePostDto in postService.searchPosts(keyword = "1"))
            println(responsePostDto)
    }
}
