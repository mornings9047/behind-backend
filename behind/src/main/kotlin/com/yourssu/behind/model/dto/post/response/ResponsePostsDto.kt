package com.yourssu.behind.model.dto.post.response

import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.PostType
import java.time.LocalDateTime

class ResponsePostsDto(post: Post) {
    val lectureId: Long? = post.lecture.id
    val postId: Long? = post.id
    val title: String = resizeText(post.title, 20)
    val type: PostType = post.type
    val content: String = resizeText(post.content, 50)
    val imgUrl: String? = post.imgUrl
    val createdAt: LocalDateTime = post.createdAt
    val commentsNum = post.comments.size

    private fun resizeText(text: String, size: Int): String {
        if (text.length > size)
            return "${text.dropLast(size)}..."
        return text
    }
}
