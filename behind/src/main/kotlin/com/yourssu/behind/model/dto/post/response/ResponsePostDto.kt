package com.yourssu.behind.model.dto.post.response

import com.yourssu.behind.model.dto.lecture.ReturnLectureDto
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.PostType
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

@ApiModel
class ResponsePostDto(post: Post) {
    @ApiModelProperty(value = "게시물Id")
    val postId: Long? = post.id

    @ApiModelProperty("강의 id")
    val lectureId = post.lecture.id

    @ApiModelProperty(value = "게시물 제목")
    val title: String = post.title

    @ApiModelProperty(value = "게시물 종류(INFORMATION,FREE,QUESTION")
    val type: PostType = post.type

    @ApiModelProperty(value = "게시물 본문")
    val content: String = post.content

    @ApiModelProperty(value = "게시물 이미지 경로")
    val imgUrl: String? = post.imgUrl

    @ApiModelProperty(value = "작성 시간")
    val createdAt: LocalDateTime = post.createdAt
}
