package com.yourssu.behind.model.dto.post.response

import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.PostType
import java.time.LocalDateTime

/*
    강의Id를 타고 들어갔을 때 게시판의 분류에맞게(게시물에 들어가는 것이아님) 리스트를 보여줄 때 리턴 해줄 DTO
 */
class ResponsePostsDto(post: Post) {
    val lectureId: Long? = post.lecture.id
    val postId: Long? = post.id
    val title: String = post.title
    val type: PostType = post.type
    val imgUrl: String? = post.imgUrl
    val content: String = post.content
    val createdAt: LocalDateTime = post.createdAt
    val thumbsUp = post.likeUser.size
    val scrapNum = post.scrapUser.size
    val commentsNum = post.comments.size
}
