package com.yourssu.behind.model.entity.lecture

import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.professor.Professor
import com.yourssu.behind.model.entity.user.User
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@SequenceGenerator(name = "id_seq", sequenceName = "id_seq", initialValue = 1, allocationSize = 1)
data class Lecture(
        @Id
        @Column(length = 20)
        val lectureCode: String,

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        val major: String,

        val year: String,

        @Enumerated(EnumType.STRING)
        val semester: LectureSemester,

        val courseName: String,

        @ManyToOne
        val professor: Professor,

        @OneToMany(mappedBy = "lecture")
        var posts: MutableList<Post> = mutableListOf(),

        @ManyToMany(mappedBy = "lectures")
        var users: MutableList<User> = mutableListOf()
) {
    @Override
    override fun toString(): String {
        return ToStringBuilder
                .reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
    }
}
