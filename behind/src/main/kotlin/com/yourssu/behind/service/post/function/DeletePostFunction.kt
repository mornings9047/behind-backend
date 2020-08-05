package com.yourssu.behind.service.post.function

import com.yourssu.behind.exception.post.PostNotExistsException
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.post.ScrapRepository

class DeletePostFunction(private val postRepository: PostRepository, private val scrapRepository: ScrapRepository) {

    fun deletePost(postId: Long) {
        val post = postRepository.findById(postId).orElseThrow { PostNotExistsException() }
        scrapRepository.deleteAllByScrapPost(post)
        post.comments.map { it.deleteComment = true }
        post.deletePost = true
        postRepository.save(post)
    }
}