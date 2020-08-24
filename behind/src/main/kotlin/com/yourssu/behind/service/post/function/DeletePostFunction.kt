package com.yourssu.behind.service.post.function

import com.yourssu.behind.exception.post.PostNotExistsException
import com.yourssu.behind.exception.post.PostOwnerNotMatchedException
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.post.ScrapRepository
import com.yourssu.behind.service.auth.JwtService

class DeletePostFunction(private val postRepository: PostRepository,
                         private val scrapRepository: ScrapRepository,
                         val jwtService: JwtService) {
    fun deletePost(postId: Long) {
        val post = postRepository.findById(postId).orElseThrow { PostNotExistsException() }
        val user = jwtService.getUser()
        if(post.user.id != user.id)
            throw PostOwnerNotMatchedException()
        scrapRepository.deleteAllByScrapPost(post)
        post.comments.map { it.deleteComment = true }
        post.deletePost = true
        postRepository.save(post)
    }
}
