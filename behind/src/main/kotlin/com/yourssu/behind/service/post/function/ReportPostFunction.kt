package com.yourssu.behind.service.post.function

import com.yourssu.behind.exception.post.PostNotExistsException
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.post.ScrapRepository

class ReportPostFunction(private val postRepository: PostRepository, private val scrapRepository: ScrapRepository) {

    fun reportPost(postId: Long) {
        val post: Post = postRepository.findById(postId).orElseThrow { PostNotExistsException() }
        if (post.reportNum >= 2) {
            post.deletePost = true
            scrapRepository.deleteAllByScrapPost(post)
            post.comments.map { it.deleteComment = true }
        } else
            post.reportNum++
        postRepository.save(post)
    }
}
