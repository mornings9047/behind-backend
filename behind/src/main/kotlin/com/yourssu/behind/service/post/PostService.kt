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
import com.yourssu.behind.service.post.function.ImgUploadFunction
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

    fun getPosts(lectureId: Long, type: PostType?): List<ResponsePostsDto> {
        var lecture = lectureRepository.findById(lectureId).orElseThrow { LectureNotExistException() }
        if (type == null)
            return getAllPosts(lecture)
        else {
            return getPostsByType(lecture, type)
        }
    }

    fun getAllPosts(lecture: Lecture): List<ResponsePostsDto> {
        return postRepository.findAllByLecture(lecture).map { ResponsePostsDto(it) }
    }

    fun getPostsByType(lecture: Lecture, type: PostType): List<ResponsePostsDto> {
        return postRepository.findAllByLectureAndTypeEquals(lecture, type).map { ResponsePostsDto(it) }
    }

}
