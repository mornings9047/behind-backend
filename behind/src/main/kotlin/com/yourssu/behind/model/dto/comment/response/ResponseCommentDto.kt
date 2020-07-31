package com.yourssu.behind.model.dto.comment.response

import com.yourssu.behind.model.entity.comment.Comment
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

@ApiModel
class ResponseCommentDto(comment: Comment) {

    @ApiModelProperty(value = "글 작성자 여부")
    var postOwner: Boolean = comment.postOwner

    @ApiModelProperty(value = "본문")
    val content: String = comment.content

    @ApiModelProperty("작성 시간")
    val createdAt: LocalDateTime = comment.createdAt

    @ApiModelProperty("대 댓글")
    val reComment: List<ResponseCommentDto>? = comment.children.map { ResponseCommentDto(it) }
}
