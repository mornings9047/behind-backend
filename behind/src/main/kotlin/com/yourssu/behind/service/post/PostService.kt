package com.yourssu.behind.service.post

import com.yourssu.behind.exception.lecture.LectureNotExistException
import com.yourssu.behind.exception.user.UserNotExistException
import com.yourssu.behind.model.dto.post.request.CreateOrUpdateRequestPostDto
import com.yourssu.behind.model.dto.post.response.ResponsePostDto
import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.PostType
import com.yourssu.behind.repository.lecture.LectureRepository
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.user.UserRepository
import com.yourssu.behind.service.post.function.FindPostFunction
import com.yourssu.behind.service.post.function.ImgUploadFunction
import com.yourssu.behind.service.post.function.ScrapFunction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PostService @Autowired constructor(private val postRepository: PostRepository,
                                         private val userRepository: UserRepository,
                                         val lectureRepository: LectureRepository) {

    private val imgUploadFunction = ImgUploadFunction()
    private val findPostFunction = FindPostFunction(postRepository)
    private val scrapFunction = ScrapFunction(userRepository, postRepository)

    fun createPost(createOrUpdateRequestPostDto: CreateOrUpdateRequestPostDto, imgFile: MultipartFile?) {
        var imgUrl: String? = null
        val user = userRepository.findBySchoolId(createOrUpdateRequestPostDto.schoolId).orElseThrow { UserNotExistException() }
        val lecture = lectureRepository.findById(createOrUpdateRequestPostDto.lectureId).orElseThrow { LectureNotExistException() }

        if (imgFile != null)
            imgUrl = imgUploadFunction.storeImg(imgFile)

        postRepository.save(Post(user = user,
                type = createOrUpdateRequestPostDto.type,
                title = createOrUpdateRequestPostDto.title,
                content = createOrUpdateRequestPostDto.content,
                imgUrl = imgUrl,
                lecture = lecture,
                deletePost = false
        ))
    }

    fun getPosts(lectureId: Long, type: PostType?, page: Int): List<ResponsePostsDto> {
        val lecture = lectureRepository.findById(lectureId).orElseThrow { LectureNotExistException() }
        return if (type == null)
            findPostFunction.getAllPosts(lecture, page)
        else
            findPostFunction.getPostsByType(lecture, type, page)
    }

    fun searchPosts(keyword: String, page: Int): List<ResponsePostsDto> {
        return findPostFunction.searchPostsByKeyword(keyword, page)
    }
    fun scrapPost(schoolId: String, postId: Long) {
        return scrapFunction.createScrapPost(schoolId, postId)
    }

    fun getPostDetails(postId: Long): ResponsePostDto {
        return findPostFunction.getPostDetails(postId)
    }
}
