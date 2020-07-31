package com.yourssu.behind.model.dto.comment.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
class CreateOrUpdateRequestCommentDto(
        @ApiModelProperty(value = "댓글 본문")
        val content: String)
