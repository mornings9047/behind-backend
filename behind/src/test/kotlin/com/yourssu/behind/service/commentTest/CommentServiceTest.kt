package com.yourssu.behind.service.commentTest

import com.yourssu.behind.exception.post.PostNotExistException
import com.yourssu.behind.exception.user.UserNotExistsException
import com.yourssu.behind.model.dto.comment.request.CreateOrUpdateRequestCommentDto
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.user.User
import com.yourssu.behind.repository.comment.CommentRepository
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.user.UserRepository
import com.yourssu.behind.service.comment.CommentService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional

/*
  schoolId가 2020인 유저가 DB에있고, PK가 1번인 POST가 DB에있다고 가정하고 진행했습니다.
 */

@SpringBootTest
class CommentServiceTest @Autowired constructor(val commentService: CommentService,
                                                val commentRepository: CommentRepository,
                                                val userRepository: UserRepository,
                                                val postRepository: PostRepository) {

    val existId: Long = 1

    @Test
    fun createCommentTest() {
        val createOrUpdateRequestCommentDto = CreateOrUpdateRequestCommentDto("TestComment")
        commentService.createComment(existId, createOrUpdateRequestCommentDto)

        val user: User = userRepository.findBySchoolId("20202020").orElseThrow { UserNotExistsException() }
        val post: Post = postRepository.findById(existId).orElseThrow { PostNotExistException() }

        Assertions.assertNotNull(commentRepository.findByUserAndPost(user, post))

    }

    @Test
    @Transactional
    fun getCommentTest() {
        val nowPage: Int = 0
        val comments = commentService.getComment(existId, 0)

        for(comment in comments)
        {
            println(comment)
        }
    }


}