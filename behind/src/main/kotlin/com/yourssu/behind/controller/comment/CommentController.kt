package com.yourssu.behind.controller.comment

import com.yourssu.behind.model.dto.comment.request.CreateOrUpdateRequestCommentDto
import com.yourssu.behind.model.dto.comment.response.ResponseCommentDto
import com.yourssu.behind.service.comment.CommentService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController @Autowired constructor(val commentService: CommentService) {
    @ApiOperation(value = "댓글 작성")
    @PostMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun createComment(@PathVariable postId: Long, @RequestBody createOrUpdateRequestCommentDto: CreateOrUpdateRequestCommentDto) {
        return commentService.createComment(postId, createOrUpdateRequestCommentDto)
    }

    @ApiOperation(value = "대 댓글 작성")
    @PostMapping("/post/{postId}/recomment/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun createReComment(@PathVariable postId: Long, @PathVariable commentId: Long, @RequestBody createOrUpdateRequestCommentDto: CreateOrUpdateRequestCommentDto) {
        commentService.createRecomment(postId, createOrUpdateRequestCommentDto, commentId)
    }

    @ApiOperation("게시물의 댓글 가져오기")
    @GetMapping("/post/{postId}")
    fun getComment(@PathVariable postId: Long): List<ResponseCommentDto> {
        return commentService.getComment(postId)
    }

    @ApiOperation("댓글 신고하기")
    @GetMapping("/report/{commentId}")
    fun reportComment(@PathVariable commentId: Long) {
        return commentService.reportComment(commentId)
    }

    @ApiOperation("댓글 삭제하기")
    @DeleteMapping("/{commentId}")
    fun deleteComment(@PathVariable commentId: Long) {
        return commentService.deleteComment(commentId)
    }
}
