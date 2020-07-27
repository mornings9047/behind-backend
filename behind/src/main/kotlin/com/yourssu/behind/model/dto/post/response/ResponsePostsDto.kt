package com.yourssu.behind.model.dto.post.response

import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.PostType
import java.time.LocalDateTime

class ResponsePostsDto(post: Post) {
    val lectureId: Long? = post.lecture.id
    val postId: Long? = post.id
    val title: String = post.title
    val type: PostType = post.type
    val imgUrl: String? = post.imgUrl
    val content: String = post.content
    val createdAt: LocalDateTime = post.createdAt
    val commentsNum = post.comments.size
}
