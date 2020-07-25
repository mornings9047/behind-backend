package com.yourssu.behind.controller.comment

import com.yourssu.behind.model.dto.comment.request.CreateOrUpdateRequestCommentDto
import com.yourssu.behind.service.comment.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController @Autowired constructor(val commentService: CommentService) {
    @PostMapping("/{postId}")
    fun createComment(@PathVariable postId: Long, @RequestBody createOrUpdateRequestCommentDto: CreateOrUpdateRequestCommentDto) {
        return commentService.createComment(postId, createOrUpdateRequestCommentDto)
    }
}