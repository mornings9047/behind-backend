package com.yourssu.behind.service.post.function

import com.yourssu.behind.exception.post.PostNotExistException
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.post.ScrapRepository

class ReportPostFunction(private val postRepository: PostRepository, private val scrapRepository: ScrapRepository) {

    fun reportPost(postId: Long) {

        var post: Post = postRepository.findById(postId).orElseThrow { PostNotExistException() }

        if (post.reportNum >= 2) {
            post.deletePost = true
            scrapRepository.deleteAllByScrapPost(post)
            post.comments.map { it.deleteComment = true }
        } else {
            post.reportNum++
        }
        postRepository.save(post)
    }
}