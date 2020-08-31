package com.yourssu.behind.model.entity.user

import com.yourssu.behind.model.entity.comment.Comment
import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.post.Post
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
data class User(
        var schoolId: String,

        @NotBlank(message = "대소문자, 숫자로 구성")
        @Size(min = 8, max = 20)
        val password: String,

        val regDate: LocalDateTime = LocalDateTime.now(),

        @Id @GeneratedValue val id: Long? = null,

        @ManyToMany
        @JoinTable(name = "courses")
        var lectures: MutableList<Lecture> = mutableListOf(),

        @OneToMany(mappedBy = "user")
        var comments: MutableList<Comment> = mutableListOf()
) {
    @Override
    override fun toString(): String {
        return ToStringBuilder
                .reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
    }
}
