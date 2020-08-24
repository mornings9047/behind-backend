package com.yourssu.behind.model.dto.comment.response

import com.yourssu.behind.model.entity.comment.Comment
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

@ApiModel
class ResponseCommentDto(comment: Comment) {

    @ApiModelProperty(value = "댓글 Id")
    val commentId: Long? = comment.id

    @ApiModelProperty(value = "글 작성자 여부")
    var postOwner: Boolean = comment.postOwner

    @ApiModelProperty(value = "본문")
    val content: String = comment.content

    @ApiModelProperty("작성 시간")
    val createdAt: LocalDateTime = comment.createdAt

    @ApiModelProperty("삭제 된 댓글인지 여부")
    var isDeleted: Boolean = comment.deleteComment

    @ApiModelProperty("대 댓글")
    var reComment: List<ResponseCommentDto>? = null

    constructor(comment: Comment, reCommentList: List<Comment>) : this(comment) {
        reComment = reCommentList.map { ResponseCommentDto(it) }
    }
}
