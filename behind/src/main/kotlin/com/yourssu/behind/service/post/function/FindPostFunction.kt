package com.yourssu.behind.service.post.function

import com.yourssu.behind.exception.post.DeletedPostException
import com.yourssu.behind.exception.post.PostNotExistsException
import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.post.PostPage
import com.yourssu.behind.model.entity.post.PostType
import com.yourssu.behind.repository.comment.CommentRepository
import com.yourssu.behind.repository.post.PostRepository

class FindPostFunction(private val postRepository: PostRepository, private val commentRepository: CommentRepository) {
    fun getAllPosts(lecture: Lecture, page: Int): List<ResponsePostsDto> {
        return postRepository.findAllByLectureAndDeletePostIsFalse(lecture, PostPage(page)).map { ResponsePostsDto(it, commentRepository.countByPostAndDeleteCommentIsFalse(it)) }
    }

    fun getPostsByType(lecture: Lecture, type: PostType, page: Int): List<ResponsePostsDto> {
        return postRepository.findAllByLectureAndTypeEqualsAndDeletePostIsFalse(lecture, type, PostPage(page)).map { ResponsePostsDto(it, commentRepository.countByPostAndDeleteCommentIsFalse(it)) }
    }

    fun searchPostsByKeyword(keyword: String, type: PostType?, page: Int): List<ResponsePostsDto> {
        val posts = if (type == null)
            postRepository.findByTitleContainingOrContentContainingAndDeletePostIsFalse(keyword, keyword, PostPage(page))
        else {
            postRepository.findByTypeAndTitleContainingAndDeletePostIsFalseOrTypeAndContentContainingAndDeletePostIsFalse(type, keyword, type, keyword)
        }
        if (posts.isEmpty())
            throw PostNotExistsException()
        return posts.map { ResponsePostsDto(it, commentRepository.countByPostAndDeleteCommentIsFalse(it)) }
    }

    fun getPostDetails(postId: Long): ResponsePostsDto {
        val post = postRepository.findByIdAndDeletePostIsFalse(postId).orElseThrow { throw PostNotExistsException() }
        if (post.deletePost)
            throw DeletedPostException()
        return ResponsePostsDto(post, commentRepository.countByPostAndDeleteCommentIsFalse(post))
    }
}
