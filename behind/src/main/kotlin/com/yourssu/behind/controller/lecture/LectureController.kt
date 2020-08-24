package com.yourssu.behind.controller.lecture

import com.yourssu.behind.model.dto.lecture.ReturnLectureDto
import com.yourssu.behind.model.entity.lecture.SearchType
import com.yourssu.behind.service.lecture.LectureService
import com.yourssu.behind.service.user.UserService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/lecture")
class LectureController @Autowired constructor(val lectureService: LectureService,
                                               val userService: UserService) {
    @ApiOperation(value = "강의목록 파일을 읽어서 DB에 저장")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/read")
    fun lectureRead() {
        return lectureService.readExcel()
    }

    @PostMapping("/search/{keyword}")
    @ApiOperation("키워드에 따른 강의 검색 (교수명 or 과목명으로 선택하여 검색)")
    @ResponseStatus(HttpStatus.OK)
    fun searchLectures(@PathVariable keyword: String, @RequestParam(required = false) type: SearchType): Collection<ReturnLectureDto> {
        return userService.searchLecture(keyword, type)
    }

    @GetMapping("/{lectureId}")
    @ApiOperation("이번 학기에 수강중인 강의목록에 선택한 강의 추가")
    fun addUserLecture(@PathVariable lectureId: Long) {
        return userService.addUserLecture(lectureId)
    }

    @DeleteMapping("/{lectureId}")
    @ApiOperation("이번 학기에 수강중인 강의목록에 선택한 강의 제거")
    fun deleteLecture(@PathVariable lectureId: Long) {
        return userService.deleteUserLecture(lectureId)
    }
}
