package com.yourssu.behind.model.dto.lecture

import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.lecture.LectureSemester
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
class ReturnLectureDto(lecture: Lecture) {
    val lectureCode: Long? = lecture.lectureCode

    @ApiModelProperty("전곻")
    val major: String = lecture.major

    @ApiModelProperty(value = "년도")
    val year: String = lecture.year

    @ApiModelProperty("학기(SPRING,SUMMER,FALL,WINTER)")
    val semester: LectureSemester = lecture.semester

    @ApiModelProperty(value = "과목명")
    val courseName = lecture.courseName

    @ApiModelProperty("교수명")
    val professor = lecture.professor.name
}