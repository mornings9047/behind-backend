package com.yourssu.behind.repository.post

import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.PostType
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    fun findAllByLectureAndDeletePostIsFalse(lecture: Lecture, pageable: Pageable): List<Post>
    fun findAllByLectureAndTypeEqualsAndDeletePostIsFalse(lecture: Lecture, type: PostType, pageable: Pageable): List<Post>
    fun findByTitleContainingOrContentContaining(title: String, content: String): List<Post>
}
