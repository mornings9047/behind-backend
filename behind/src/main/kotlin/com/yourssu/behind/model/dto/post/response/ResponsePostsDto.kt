package com.yourssu.behind.model.dto.post.response

import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.PostType
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

@ApiModel
class ResponsePostsDto(post: Post, commentNum: Int) {
    @ApiModelProperty(value = "게시글 Id")
    val postId: Long? = post.id

    @ApiModelProperty(value = "과목코드")
    val lectureCode: Long? = post.lecture.lectureCode

    @ApiModelProperty(value = "게시글 제목")
    val title: String = post.title

    @ApiModelProperty(value = "게시글 종류")
    val type: PostType = post.type

    @ApiModelProperty(value = "본문")
    val content: String = post.content

    @ApiModelProperty(value = "게시글 이미지 경로")
    val imgUrl: String? = post.imgUrl

    @ApiModelProperty(value = "작성 시간")
    val createdAt: LocalDateTime = post.createdAt

    @ApiModelProperty(value = "댓글 갯수")
    val commentsNum = commentNum

}
