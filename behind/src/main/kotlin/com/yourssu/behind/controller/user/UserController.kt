package com.yourssu.behind.controller.user

import com.yourssu.behind.model.dto.lecture.ReturnLectureDto
import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.lecture.SearchType
import com.yourssu.behind.model.entity.post.PostSearch
import com.yourssu.behind.service.user.UserService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController @Autowired constructor(val userService: UserService) {

    @GetMapping("/post")
    @ApiOperation("유저와 연관된 게시물 가져오기(댓글 작성한 글, 작성한 글, 스크랩 한 글")
    @ResponseStatus(HttpStatus.OK)
    fun getUserRelatedPost(@RequestParam type: PostSearch, page: Int): Collection<ResponsePostsDto> {
        return userService.findUserRelatedPost(type, page)
    }

    @DeleteMapping
    @ApiOperation("회원 탈퇴")
    @ResponseStatus(HttpStatus.OK)
    fun deleteUser() {
        return userService.deleteUser()
    }

    @PostMapping("/search/{keyword}")
    @ApiOperation("키워드에 따른 검색. (교수명 or 과목명으로 선택하여 검색)")
    fun searchLectures(@PathVariable keyword:String, @RequestParam(required = false) type: SearchType): Collection<ReturnLectureDto>{
        return userService.searchLecture(keyword,type)
    }

    @PostMapping("/{lectureId}")
    @ApiOperation("이번 학기에 수강중인 강의목록에 선택한 강의 추가")
    fun addUserLecture(@PathVariable lectureId: Long){
        return userService.addUserLecture(lectureId)
    }

    @DeleteMapping("/{lectureId}")
    @ApiOperation("이번 학기에 수강중인 강의목록에 선택한 강의 제거")
    fun deleteLecture(@PathVariable lectureId: Long){
        return userService.deleteUserLecture(lectureId)
    }

    @GetMapping("/lectures")
    @ApiOperation("저장한 강의들 보기")
    fun getAllLecture() : Collection<ReturnLectureDto>{
        return userService.getAllUserLecture()
    }

    @GetMapping("/feed")
    @ApiOperation("새 글 피드")
    fun newPostFeed() : Collection<ResponsePostsDto>{
        return userService.newPostFeed()
    }

}
