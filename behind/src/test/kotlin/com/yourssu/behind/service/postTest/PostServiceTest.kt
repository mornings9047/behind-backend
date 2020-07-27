package com.yourssu.behind.service.postTest

import com.yourssu.behind.exception.lecture.LectureNotExistException
import com.yourssu.behind.exception.post.PostNotExistException
import com.yourssu.behind.model.dto.post.request.CreateOrUpdateRequestPostDto
import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.lecture.LectureSemester
import com.yourssu.behind.model.entity.post.PostType
import com.yourssu.behind.model.entity.professor.Professor
import com.yourssu.behind.repository.lecture.LectureRepository
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.professor.ProfessorRepository
import com.yourssu.behind.service.post.PostService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.event.annotation.BeforeTestExecution
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

/*
  schoolId가 2020인 유저가 DB에있다고 가정했습니다.
 */

@SpringBootTest
class PostServiceTest @Autowired constructor(val postService: PostService,
                                             val postRepository: PostRepository,
                                             val professorRepository: ProfessorRepository,
                                             val lectureRepository: LectureRepository) {

    val testCreateOrUpdateRequestPostDto =
            CreateOrUpdateRequestPostDto(schoolId = "20202020",
                    title = "testPost3",
                    type = PostType.FREE,
                    content = "This is PostCreation Test",
                    lectureId = 1)

    val imgFile: MultipartFile? = MockMultipartFile("testImgFile.jpeg",
            "testImgFile", null, java.io.File("C:\\Users\\82102\\Desktop\\2020_behind_backend\\uploads\\0fee34e5-38da-46be-9bb8-92711ee9a2bf.jpeg").inputStream())

    val existId: Long = 1
    val fakeId: Long = -1

    @Test
    @Transactional
    @BeforeTestExecution
    fun createPostTest() {
        postService.createPost(testCreateOrUpdateRequestPostDto, imgFile)
        Assertions.assertNotNull(postRepository.findByTitle(testCreateOrUpdateRequestPostDto.title))
    }

    @Test
    @Transactional
    fun getPostTest() {
        Assertions.assertNotNull(postService.getPosts(existId, null, 0))
        Assertions.assertThrows(LectureNotExistException::class.java
        ) { postService.getPosts(fakeId, null, 0) }
    }

    @Test
    @Transactional
    fun scrapTest() {
        postService.scrapPost("20202020", existId)
        val post = postRepository.findById(existId).orElseThrow { PostNotExistException() }

        Assertions.assertNotEquals(0, post.scrapUser.size)
    }

    @Test
    @Transactional
    fun createPostsTest() {
        val professor = Professor(name = "JOC")
        professorRepository.save(professor)
        lectureRepository.save(Lecture(major = "computer", year = "20", semester = LectureSemester.SPRING, courseName = "OOP", professor = professor))

        for (i in 1..100)
            postService.createPost(CreateOrUpdateRequestPostDto(schoolId = "20202020", title = "title$i", type = PostType.FREE, content = "www.figma.com/file/gEIPNEmE2KpymmVGFbHdNP/%EB%B9%84%ED%95%98%EC%9D%B8%EB%93%9C---Android?node-id=59%3A1239https://www.figma.com/file/gEIPNEmE2KpymmVGFbHdNP/%EB%B9%84%ED%95%98%EC%9D%B8%EB%93%9C---Android?node-id=59%3A1239$i", lectureId = 422), imgFile = null)
    }

    @Test
    @Transactional
    fun searchPostsTest() {
        for (i in 1..20)
            postService.createPost(CreateOrUpdateRequestPostDto(schoolId = "20202020", title = "FREE$i", type = PostType.FREE, content = "www.figma.com/file/gEIPNEmE2KpymmVGFbHdNP/%EB%B9%84%ED%95%98%EC%9D%B8%EB%93%9C---Android?node-id=59%3A1239https://www.figma.com/file/gEIPNEmE2KpymmVGFbHdNP/%EB%B9%84%ED%95%98%EC%9D%B8%EB%93%9C---Android?node-id=59%3A1239$i", lectureId = 9), imgFile = null)
        for (i in 1..20)
            postService.createPost(CreateOrUpdateRequestPostDto(schoolId = "20202020", title = "QUES$i", type = PostType.QUESTION, content = "www.figma.com/file/gEIPNEmE2KpymmVGFbHdNP/%EB%B9%84%ED%95%98%EC%9D%B8%EB%93%9C---Android?node-id=59%3A1239https://www.figma.com/file/gEIPNEmE2KpymmVGFbHdNP/%EB%B9%84%ED%95%98%EC%9D%B8%EB%93%9C---Android?node-id=59%3A1239$i", lectureId = 9), imgFile = null)
        for (i in 1..20)
            postService.createPost(CreateOrUpdateRequestPostDto(schoolId = "20202020", title = "INFO$i", type = PostType.INFORMATION, content = "www.figma.com/file/gEIPNEmE2KpymmVGFbHdNP/%EB%B9%84%ED%95%98%EC%9D%B8%EB%93%9C---Android?node-id=59%3A1239https://www.figma.com/file/gEIPNEmE2KpymmVGFbHdNP/%EB%B9%84%ED%95%98%EC%9D%B8%EB%93%9C---Android?node-id=59%3A1239$i", lectureId = 9), imgFile = null)

        for (responsePostDto in postService.searchPosts(keyword = "FREE", type = null, page = 0))
            println("${responsePostDto.type}   ${responsePostDto.title}")
        for (responsePostDto in postService.searchPosts(keyword = "QUES", type = null, page = 0))
            println("${responsePostDto.type}   ${responsePostDto.title}")
        for (responsePostDto in postService.searchPosts(keyword = "INFO", type = null, page = 0))
            println("${responsePostDto.type}   ${responsePostDto.title}")
    }

    @Test
    fun getPostDetailsTest() {
        println(postService.getPostDetails(160).content)
    }
}
