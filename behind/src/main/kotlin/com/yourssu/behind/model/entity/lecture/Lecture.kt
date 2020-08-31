package com.yourssu.behind.model.entity.lecture

import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.professor.Professor
import com.yourssu.behind.model.entity.user.User
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import javax.persistence.*

@Entity
data class Lecture(
        @Column(length = 20)
        val lectureCode: String,

        val major: String,

        val year: String,

        @Enumerated(EnumType.STRING)
        val semester: LectureSemester,

        val courseName: String,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @ManyToOne
        val professor: Professor,

        @ManyToMany(mappedBy = "lectures")
        var users: MutableList<User> = mutableListOf()
) {
    @Override
    override fun toString(): String {
        return ToStringBuilder
                .reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
    }
}
