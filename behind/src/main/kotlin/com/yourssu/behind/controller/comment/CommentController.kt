package com.yourssu.behind.controller.comment

import com.yourssu.behind.model.dto.comment.request.CreateOrUpdateRequestCommentDto
import com.yourssu.behind.model.dto.comment.response.ResponseCommentDto
import com.yourssu.behind.service.comment.CommentService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController @Autowired constructor(val commentService: CommentService) {

    @ApiOperation(value = "댓글 작성")
    @PostMapping("/post/{postId}")
    fun createComment(@PathVariable postId: Long, @RequestBody createOrUpdateRequestCommentDto: CreateOrUpdateRequestCommentDto) {
        return commentService.createComment(postId, createOrUpdateRequestCommentDto)
    }

    @ApiOperation(value = "대 댓글 작성")
    @PostMapping("/post/{postId}/recomment/{commentId}")
    fun createReComment(@PathVariable postId: Long, @PathVariable commentId: Long, @RequestBody createOrUpdateRequestCommentDto: CreateOrUpdateRequestCommentDto) {
        commentService.createRecomment(postId, createOrUpdateRequestCommentDto, commentId)
    }

    @ApiOperation("게시물의 댓글 가져오기")
    @GetMapping("/post/{postId}")
    fun getComment(@PathVariable postId: Long, @RequestParam page: Int): List<ResponseCommentDto> {
        return commentService.getComment(postId, page)
    }
}
