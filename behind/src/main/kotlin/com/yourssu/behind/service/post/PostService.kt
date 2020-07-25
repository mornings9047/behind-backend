package com.yourssu.behind.service.post

import com.yourssu.behind.exception.lecture.LectureNotExistException
import com.yourssu.behind.exception.user.UserNotExistException
import com.yourssu.behind.model.dto.post.request.CreateOrUpdateRequestPostDto
import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.PostType
import com.yourssu.behind.model.entity.user.User
import com.yourssu.behind.repository.lecture.LectureRepository
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.user.UserRepository
import com.yourssu.behind.service.post.function.FindPostFunction
import com.yourssu.behind.service.post.function.ImgUploadFunction
import com.yourssu.behind.service.post.function.ScrapFunction
import com.yourssu.behind.service.post.function.ThumbsUpFunction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PostService @Autowired constructor(val postRepository: PostRepository
                                         , val userRepository: UserRepository,
                                         val lectureRepository: LectureRepository) {

    private val imgUploadFunction = ImgUploadFunction()
            private val findPostFunction = FindPostFunction(postRepository)
            private val thumbsUpFunction = ThumbsUpFunction(userRepository, postRepository)
            private val scrapFunction = ScrapFunction(userRepository, postRepository)

            fun createPost(createOrUpdateRequestPostDto: CreateOrUpdateRequestPostDto, imgFile: MultipartFile?): Unit {
        var imgUrl: String? = null
        var user: User = userRepository.findBySchoolId(createOrUpdateRequestPostDto.schoolId).orElseThrow { UserNotExistException() }
        var lecture: Lecture = lectureRepository.findById(createOrUpdateRequestPostDto.lectureId).orElseThrow { LectureNotExistException() }

        if (imgFile != null)
            imgUrl = imgUploadFunction.storeImg(imgFile)

        postRepository.save(Post(user = user,
                type = createOrUpdateRequestPostDto.type,
                title = createOrUpdateRequestPostDto.title,
                content = createOrUpdateRequestPostDto.content,
                imgUrl = imgUrl,
                lecture = lecture
        ))
    }

    fun getPosts(lectureId: Long, type: PostType?, page: Int): List<ResponsePostsDto> {
        var lecture = lectureRepository.findById(lectureId).orElseThrow { LectureNotExistException() }
        return if (type == null)
            findPostFunction.getAllPosts(lecture, page)
        else {
            findPostFunction.getPostsByType(lecture, type, page)
        }
        println("Finish")
    }

    fun thumbsUp(schoolId: String, postId: Long) {
        return thumbsUpFunction.thumbsUp(schoolId, postId)
    }

    fun scrapPost(schoolId: String, postId: Long) {
        return scrapFunction.createScrapPost(schoolId, postId)
    }
}
