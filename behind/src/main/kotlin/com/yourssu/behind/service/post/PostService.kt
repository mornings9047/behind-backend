package com.yourssu.behind.service.post

import com.yourssu.behind.exception.lecture.LectureNotExistException
import com.yourssu.behind.exception.post.PostNotExistException
import com.yourssu.behind.exception.user.UserNotExistException
import com.yourssu.behind.model.dto.post.request.CreateOrUpdateRequestPostDto
import com.yourssu.behind.model.dto.post.response.ResponsePostDto
import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.user.User
import com.yourssu.behind.repository.lecture.LectureRepository
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PostService @Autowired constructor(val postRepository: PostRepository
                                         , val userRepository: UserRepository,
                                         val lectureRepository: LectureRepository) {

    private val imgUploadFunction = ImgUploadFunction()

    fun createPost(createOrUpdateRequestPostDto: CreateOrUpdateRequestPostDto, imgFile: MultipartFile): Unit {
        var imgUrl: String = imgUploadFunction.storeImg(imgFile)
        var user: User = userRepository.findBySchoolId(createOrUpdateRequestPostDto.schoolId).orElseThrow { UserNotExistException() }
        var lecture: Lecture = lectureRepository.findById(createOrUpdateRequestPostDto.lectureId).orElseThrow { LectureNotExistException() }


        postRepository.save(Post(user = user,
                type = createOrUpdateRequestPostDto.type,
                title = createOrUpdateRequestPostDto.title,
                content = createOrUpdateRequestPostDto.content,
                imgUrl = imgUrl,
                lecture = lecture
        ))
    }

    fun findPostById(id: Long): ResponsePostDto {
        var post: Post = postRepository.findById(id).orElseThrow { PostNotExistException() }
        return ResponsePostDto(post)
    }

    fun findPostListByType(imgFile: MultipartFile) {

    }

    fun createPostImg(imgFile: MultipartFile): String {
        return imgUploadFunction.storeImg(imgFile)
    }
}
