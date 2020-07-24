package com.yourssu.behind.service

import com.yourssu.behind.service.post.PostService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PostServiceTests {
    @Autowired
    lateinit var postService: PostService

    @Test
    fun searchPostsTest() {

    }
}
