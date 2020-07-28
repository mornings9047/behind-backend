package com.yourssu.behind.model.dto.post.request

import com.yourssu.behind.model.entity.post.PostType
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
data class CreateOrUpdateRequestPostDto(
        @ApiModelProperty("학번")
        val schoolId: String,
        @ApiModelProperty(value = "제목")
        val title: String,
        @ApiModelProperty(value = "글의 종륲 (QUESTION,FREE,INFORMATION)",required = false)
        val type: PostType,
        @ApiModelProperty(value = "본문")
        val content: String,
        @ApiModelProperty(value = "강의 Id")
        val lectureId: Long)
