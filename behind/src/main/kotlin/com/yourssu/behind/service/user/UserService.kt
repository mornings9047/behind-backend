package com.yourssu.behind.service.user

import com.yourssu.behind.exception.user.UserNotExistsException
import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.PostPage
import com.yourssu.behind.model.entity.post.PostSearch
import com.yourssu.behind.repository.comment.CommentRepository
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.post.ScrapRepository
import com.yourssu.behind.repository.user.UserRepository
import com.yourssu.behind.service.user.function.UserDeleteFunction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService @Autowired constructor(val userRepository: UserRepository, val commentRepository: CommentRepository, val postRepository: PostRepository, val scrapRepository: ScrapRepository) {

    private val deleteFunction = UserDeleteFunction(userRepository)

    @Transactional
    fun findUserRelatedPost(userId: Long, type: PostSearch, page: Int): Collection<ResponsePostsDto> {
        val user = userRepository.findById(userId).orElseThrow { UserNotExistsException() }
        val postPage = PostPage(page)
        return when (type) {
            PostSearch.SCRAP -> {
                val returnPost: MutableSet<Post> = mutableSetOf()
                scrapRepository.findAllByScrapUser(user).map { returnPost.add(it.scrapPost) }
                returnPost.map { ResponsePostsDto(it) }
            }
            PostSearch.COMMENT -> {
                val returnPost: MutableSet<Post> = mutableSetOf()
                commentRepository.findAllByUserAndDeleteCommentIsFalse(user).map { returnPost.add(it.post) }
                returnPost.map { ResponsePostsDto(it) }
            }
            PostSearch.POST -> {
                postRepository.findAllByUserAndDeletePostIsFalse(user, postPage).map { ResponsePostsDto(it) }
            }
        }
    }

    @Transactional
    fun deleteUser(userId: Long) {
        deleteFunction.deleteUser(userId)
    }
}