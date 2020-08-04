package com.yourssu.behind.repository.post

import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.PostType
import com.yourssu.behind.model.entity.user.User
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    fun findAllByLectureAndDeletePostIsFalse(lecture: Lecture, pageable: Pageable): List<Post>
    fun findAllByLectureAndTypeEqualsAndDeletePostIsFalse(lecture: Lecture, type: PostType, pageable: Pageable): List<Post>
    fun findByIdAndDeletePostIsFalse(id: Long): Optional<Post>
    fun findByTitleContainingOrContentContainingAndDeletePostIsFalse(title: String, content: String, pageable: Pageable): List<Post>
    fun findByTitleContainingOrContentContainingAndTypeAndDeletePostIsFalse(title: String, content: String, type: PostType, pageable: Pageable): List<Post>
    fun findAllByUserAndDeletePostIsFalse(user: User, page: Pageable): List<Post>
}
