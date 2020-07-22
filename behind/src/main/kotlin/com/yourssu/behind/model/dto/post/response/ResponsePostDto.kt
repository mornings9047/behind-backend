package com.yourssu.behind.model.dto.post.response

import com.yourssu.behind.model.dto.lecture.ReturnLectureDto
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.PostType
import java.time.LocalDateTime

class ResponsePostDto(post: Post) {
    val postId : Long? = post.id
    val title: String = post.title
    val type: PostType = post.type
    val content: String = post.content
    val imgUrl : String? = post.imgUrl
    val createdAt: LocalDateTime = post.createdAt
    val lectureId = ReturnLectureDto(post.lecture)

}