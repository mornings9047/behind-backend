package com.yourssu.behind.service.userTest

import com.yourssu.behind.model.entity.post.PostSearch
import com.yourssu.behind.service.user.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional

/*
    DB에 PK 1번 유저가 있다고 가정했습니다.
 */

@SpringBootTest
class UserServiceTest @Autowired constructor(val userService: UserService) {

    @Test
    @Transactional
    fun findUserRelatedPost() {
        Assertions.assertNotNull(userService.findUserRelatedPost(1, PostSearch.POST,0))
        Assertions.assertNotNull(userService.findUserRelatedPost(1, PostSearch.COMMENT,0))
        Assertions.assertNotNull(userService.findUserRelatedPost(1, PostSearch.SCRAP,0))
    }

}