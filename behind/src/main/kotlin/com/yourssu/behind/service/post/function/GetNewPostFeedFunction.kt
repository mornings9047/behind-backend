package com.yourssu.behind.service.post.function

import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.post.PostPage
import com.yourssu.behind.repository.comment.CommentRepository
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.service.auth.JwtService

class GetNewPostFeedFunction(private val jwtService: JwtService,
                             private val postRepository: PostRepository,
                             private val commentRepository: CommentRepository){

    fun getNewPostFeed(page: Int) : Collection<ResponsePostsDto> {
        val user = jwtService.getUser()
        val result = postRepository.findAllByLecture_UsersAndDeletePostIsFalse(user, PostPage(page)).map{
            ResponsePostsDto(it, commentRepository.countByPostAndDeleteCommentIsFalse(it))
        }
        return result
    }
}