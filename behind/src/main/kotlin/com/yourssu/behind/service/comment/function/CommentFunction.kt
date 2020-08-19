package com.yourssu.behind.service.comment.function

import com.yourssu.behind.exception.comment.CommentNotExistsException
import com.yourssu.behind.exception.post.PostNotExistsException
import com.yourssu.behind.model.dto.comment.response.ResponseCommentDto
import com.yourssu.behind.model.entity.comment.Comment
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.repository.comment.CommentRepository
import com.yourssu.behind.repository.post.PostRepository

class CommentFunction(private val commentRepository: CommentRepository, private val postRepository: PostRepository) {

    fun createComment(comment: Comment) {
        commentRepository.save(comment)
    }

    fun deleteComment(commentId: Long) {
        val comment = commentRepository.findById(commentId).orElseThrow { CommentNotExistsException() }
        val post = postRepository.findByComments(comment).orElseThrow{PostNotExistsException()}

        comment.deleteComment = true
        if (comment.parent != null)
            comment.parent.reCommentNum--

        post.commentNum--
        commentRepository.save(comment)
    }

    fun getComment(postId: Long): List<ResponseCommentDto> {
        val post: Post = postRepository.findById(postId).orElseThrow { PostNotExistsException() }

        return commentRepository.findByPostAndParentIsNull(post)
                .filter { it.reCommentNum > 0 || !it.deleteComment }
                .map {
                    if (it.reCommentNum > 0)
                        ResponseCommentDto(it, commentRepository.findAllByDeleteCommentIsFalseAndParent(it))
                    else
                        ResponseCommentDto(it)
                }
    }

}
