package com.yourssu.behind.controller.user

import com.yourssu.behind.exception.user.UserNotExistsException
import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.post.PostSearch
import com.yourssu.behind.service.auth.JwtService
import com.yourssu.behind.service.user.UserService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController @Autowired constructor(val userService: UserService) {

    @GetMapping("/post")
    @ApiOperation("유저와 연관된 게시물 가져오기(댓글 작성한 글, 작성한 글, 스크랩 한 글")
    fun getUserRelatedPost(@RequestParam type: PostSearch, page: Int): ResponseEntity<Any> {
        return try {
            ResponseEntity(userService.findUserRelatedPost(type, page), HttpStatus.OK)
        } catch (e: UserNotExistsException) {
            ResponseEntity("존재하지 않는 사용자입니다", HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping()
    @ApiOperation("회원 탈퇴")
    fun deleteUser(): ResponseEntity<String> {
        return try {
            userService.deleteUser()
            ResponseEntity("로그아웃에 성공하였습니다", HttpStatus.OK)
        } catch (e: UserNotExistsException) {
            ResponseEntity("존재하지 않는 사용자입니다", HttpStatus.BAD_REQUEST)
        }
    }
}
