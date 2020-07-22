package com.yourssu.behind.repository.post

import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.PostType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    fun findAllByLecture(lecture: Lecture): List<Post>
    fun findAllByLectureAndTypeEquals(lecture: Lecture, type: PostType): List<Post>
}