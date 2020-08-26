package com.yourssu.behind.model.dto.lecture

import com.yourssu.behind.model.entity.lecture.LectureSemester
import com.yourssu.behind.model.entity.professor.Professor
import io.swagger.annotations.ApiModelProperty

class LectureCreateDto(
        @ApiModelProperty(value = "과목코드(PK)")
        val lectureCode : String,

        @ApiModelProperty(value = "과목이름")
        val courseName: String,

        @ApiModelProperty(value = "개설학과")
        val major: String,

        @ApiModelProperty(value = "교수이름")
        val professor: Professor,

        @ApiModelProperty(value = "년도")
        val year: String,
        
        @ApiModelProperty(value = "학기")
        val semester: LectureSemester
)
