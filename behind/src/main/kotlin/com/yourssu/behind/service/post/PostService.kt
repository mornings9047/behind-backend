package com.yourssu.behind.service.post

import com.yourssu.behind.exception.lecture.LectureNotExistsException
import com.yourssu.behind.model.dto.post.request.CreateOrUpdateRequestPostDto
import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.post.Image
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.PostType
import com.yourssu.behind.repository.lecture.LectureRepository
import com.yourssu.behind.repository.post.ImageRepository
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
                                         val imageRepository: ImageRepository,
                                         val userRepository: UserRepository,
                                         reportRepository: ReportRepository,
                                         val imgUploadFunction: ImgUploadFunction) {
    private val findPostFunction = FindPostFunction(postRepository)
    private val scrapFunction = ScrapFunction(jwtService, postRepository, scrapRepository)
    private val reportFunction = ReportPostFunction(postRepository, scrapRepository, reportRepository)
    private val deleteFunction = DeletePostFunction(postRepository, scrapRepository, jwtService)

    @Transactional
    fun createPost(createOrUpdateRequestPostDto: CreateOrUpdateRequestPostDto, imgFile: Array<MultipartFile>?) {
        val user = jwtService.getUser()
        val lecture = lectureRepository.findById(createOrUpdateRequestPostDto.lectureId).orElseThrow { LectureNotExistsException() }
        val post = Post(
                user = user,
                type = createOrUpdateRequestPostDto.type,
                title = createOrUpdateRequestPostDto.title,
                content = createOrUpdateRequestPostDto.content,
                lecture = lecture,
                deletePost = false
        )
        postRepository.save(post)

        if (imgFile != null) {
            val imgUrl = imgUploadFunction.s3StoreImg(imgFile).map { Image(URL = it, post = post) }
            imgUrl.forEach { imageRepository.save(it) }
            post.imageURL = imgUrl
        }
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
