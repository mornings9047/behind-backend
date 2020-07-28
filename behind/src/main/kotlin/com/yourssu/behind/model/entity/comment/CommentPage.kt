package com.yourssu.behind.model.entity.comment

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class CommentPage(nowPage: Int) : PageRequest(nowPage, 5, Sort.by("id").ascending()) {
}