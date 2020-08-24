package com.yourssu.behind.model.dto.user.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
class UserSignInRequestDto(
        @ApiModelProperty(value = "학번")
        val schoolId: String,
        @ApiModelProperty(value = "비밀번호")
        val password: String
)
