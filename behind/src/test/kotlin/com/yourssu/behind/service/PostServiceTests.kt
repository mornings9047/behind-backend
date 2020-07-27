package com.yourssu.behind.service

import com.yourssu.behind.model.dto.post.request.CreateOrUpdateRequestPostDto
import com.yourssu.behind.model.dto.user.request.UserSignUpRequestDto
import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.lecture.LectureSemester
import com.yourssu.behind.model.entity.post.PostType
import com.yourssu.behind.model.entity.professor.Professor
import com.yourssu.behind.repository.lecture.LectureRepository
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.professor.ProfessorRepository
import com.yourssu.behind.repository.user.UserRepository
import com.yourssu.behind.service.auth.AuthService
import com.yourssu.behind.service.post.PostService
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.event.annotation.AfterTestExecution
import org.springframework.test.context.event.annotation.BeforeTestExecution
import org.springframework.transaction.annotation.Transactional

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

    @Test
    @Order(0)
    fun createPosts() {
        authService.signUp(UserSignUpRequestDto("20170000", "Password123"))

        val professor = Professor(name = "JOC")
        professorRepository.save(professor)
        lectureRepository.save(Lecture(major = "computer", year = "20", semester = LectureSemester.SPRING, courseName = "OOP", professor = professor))

        for (i in 1..100)
            postService.createPost(CreateOrUpdateRequestPostDto(schoolId = "20170000", title = "title$i", type = PostType.FREE, content = "https://www.figma.com/file/gEIPNEmE2KpymmVGFbHdNP/%EB%B9%84%ED%95%98%EC%9D%B8%EB%93%9C---Android?node-id=59%3A1239https://www.figma.com/file/gEIPNEmE2KpymmVGFbHdNP/%EB%B9%84%ED%95%98%EC%9D%B8%EB%93%9C---Android?node-id=59%3A1239$i", lectureId = 422), imgFile = null)
    }

    @Test
    @Transactional
    fun searchPostsTest() {
        for (responsePostDto in postService.searchPosts(keyword = "1", page = 1))
            println("${responsePostDto.title}   ${responsePostDto.content}")
    }

    @Test
    fun getPostDetailsTest() {
        println(postService.getPostDetails(450).content)
    }
}
