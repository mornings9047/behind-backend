package com.yourssu.behind.service.user

import com.yourssu.behind.model.dto.lecture.ReturnLectureDto
import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.lecture.SearchType
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.PostPage
import com.yourssu.behind.model.entity.post.PostSearch
import com.yourssu.behind.repository.comment.CommentRepository
import com.yourssu.behind.repository.lecture.LectureRepository
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.post.ScrapRepository
import com.yourssu.behind.repository.professor.ProfessorRepository
import com.yourssu.behind.repository.user.UserRepository
import com.yourssu.behind.service.auth.JwtService
import com.yourssu.behind.service.lecture.function.UserLectureFunction
import com.yourssu.behind.service.lecture.function.FindLectureFunction
import com.yourssu.behind.service.post.function.FindPostFunction
import com.yourssu.behind.service.post.function.GetNewPostFeedFunction
import com.yourssu.behind.service.user.function.UserDeleteFunction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService @Autowired constructor(val userRepository: UserRepository,
                                         val lectureRepository: LectureRepository,
                                         val professorRepository: ProfessorRepository,
                                         val commentRepository: CommentRepository,
                                         val postRepository: PostRepository,
                                         val scrapRepository: ScrapRepository,
                                         private val jwtService: JwtService) {

    private val deleteFunction = UserDeleteFunction(userRepository, jwtService)
    private val findLectureFunction = FindLectureFunction(lectureRepository, professorRepository)
    private val userLectureFunction = UserLectureFunction(jwtService, lectureRepository)
    private val getNewPostFeedFunction = GetNewPostFeedFunction(jwtService, postRepository, commentRepository)

    @Transactional
    fun findUserRelatedPost(type: PostSearch, page: Int): Collection<ResponsePostsDto> {
        val user = jwtService.getUser()
        val postPage = PostPage(page)
        return when (type) {
            PostSearch.SCRAP -> {
                val returnPost: MutableSet<Post> = mutableSetOf()
                scrapRepository.findAllByScrapUser(user).map { returnPost.add(it.scrapPost) }
                returnPost.map { ResponsePostsDto(it, commentRepository.countByPostAndDeleteCommentIsFalse(it)) }
            }
            PostSearch.COMMENT -> {
                val returnPost: MutableSet<Post> = mutableSetOf()
                commentRepository.findAllByUserAndDeleteCommentIsFalse(user).map { returnPost.add(it.post) }
                returnPost.map { ResponsePostsDto(it, commentRepository.countByPostAndDeleteCommentIsFalse(it)) }
            }
            PostSearch.POST -> {
                postRepository.findAllByUserAndDeletePostIsFalse(user, postPage).map { ResponsePostsDto(it, commentRepository.countByPostAndDeleteCommentIsFalse(it)) }
            }
        }
    }

    @Transactional
    fun deleteUser() {
        deleteFunction.deleteUser()
    }

    @Transactional
    fun searchLecture(keyword: String, type: SearchType) : Collection<ReturnLectureDto> {
        return findLectureFunction.searchLectureByKeyword(keyword,type)
    }

    @Transactional
    fun addUserLecture(lectureId: Long){
        userLectureFunction.addUserLecture(lectureId)
    }

    @Transactional
    fun deleteUserLecture(lectureId: Long){
        userLectureFunction.removeUserLecture(lectureId)
    }

    @Transactional
    fun getAllUserLecture() : Collection<ReturnLectureDto>{
        return userLectureFunction.getUserLectures()
    }

    @Transactional
    fun newPostFeed(page: Int) : Collection<ResponsePostsDto>{
        return getNewPostFeedFunction.getNewPostFeed(page)
    }


}
