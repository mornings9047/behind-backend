package com.yourssu.behind.service.post

import com.yourssu.behind.exception.lecture.LectureNotExistsException
import com.yourssu.behind.model.dto.post.request.CreateOrUpdateRequestPostDto
import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.PostType
import com.yourssu.behind.repository.comment.CommentRepository
import com.yourssu.behind.repository.lecture.LectureRepository
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.post.ScrapRepository
import com.yourssu.behind.repository.report.ReportRepository
import com.yourssu.behind.repository.user.UserRepository
import com.yourssu.behind.service.auth.JwtService
import com.yourssu.behind.service.post.function.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class PostService @Autowired constructor(private val postRepository: PostRepository,
                                         val lectureRepository: LectureRepository,
                                         private val jwtService: JwtService,
                                         scrapRepository: ScrapRepository,
                                         val userRepository: UserRepository,
                                         val reportRepository: ReportRepository,
                                         val imgUploadFunction: ImgUploadFunction,
                                         val commentRepository: CommentRepository) {
    private val findPostFunction = FindPostFunction(postRepository, commentRepository)
    private val scrapFunction = ScrapFunction(jwtService, postRepository, scrapRepository)
    private val reportFunction = ReportPostFunction(postRepository, scrapRepository, reportRepository)
    private val deleteFunction = DeletePostFunction(postRepository, scrapRepository)

    @Transactional
    fun createPost(createOrUpdateRequestPostDto: CreateOrUpdateRequestPostDto, imgFile: MultipartFile?) {
        var imgUrl: String? = null
        val user = jwtService.getUser()
        val lecture = lectureRepository.findById(createOrUpdateRequestPostDto.lectureId).orElseThrow { LectureNotExistsException() }

        if (imgFile != null)
            imgUrl = imgUploadFunction.s3StoreImg(imgFile)

        postRepository.save(Post(user = user,
                type = createOrUpdateRequestPostDto.type,
                title = createOrUpdateRequestPostDto.title,
                content = createOrUpdateRequestPostDto.content,
                imgUrl = imgUrl,
                lecture = lecture,
                deletePost = false
        ))
    }

    @Transactional
    fun getPosts(lectureId: Long, type: PostType?, page: Int): List<ResponsePostsDto> {
        val lecture = lectureRepository.findById(lectureId).orElseThrow { LectureNotExistsException() }
        return if (type == null)
            findPostFunction.getAllPosts(lecture, page)
        else
            findPostFunction.getPostsByType(lecture, type, page)
    }

    @Transactional
    fun searchPosts(keyword: String, type: PostType?, page: Int): List<ResponsePostsDto> {
        return findPostFunction.searchPostsByKeyword(keyword, type, page)
    }

    @Transactional
    fun scrapPost(postId: Long) {
        return scrapFunction.createScrapPost(postId)
    }

    @Transactional
    fun getPostDetails(postId: Long): ResponsePostsDto {
        return findPostFunction.getPostDetails(postId)
    }

    @Transactional
    fun deletePost(postId: Long) {
        deleteFunction.deletePost(postId)
    }

    @Transactional
    fun reportPost(postId: Long) {
        reportFunction.reportPost(jwtService.getUser(), postId)
    }
}
