package com.yourssu.behind.controller.lecture

import com.yourssu.behind.service.lecture.LectureService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/lecture")
class LectureController @Autowired constructor(val lectureService: LectureService) {

    @ApiOperation(value = "강의목록 파일을 읽어서 DB에 저장")
    @GetMapping("/read")
    fun lectureRead() {
        return lectureService.readExcel()
    }
}
