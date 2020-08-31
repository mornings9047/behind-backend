package com.yourssu.behind.model.entity.post

import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.user.User
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import org.springframework.data.jpa.repository.Temporal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Post(
        @Id @GeneratedValue
        val id: Long? = null,

        @Enumerated(EnumType.STRING)
        var type: PostType,

        var title: String,

        var reportNum: Int = 0,

        var commentNum: Int = 0,

        @Lob
        var content: String,

        @Temporal
        var createdAt: LocalDateTime = LocalDateTime.now(),

        var deletePost: Boolean = false,

        @OneToMany(mappedBy = "post")
        var imageURL: List<Image> = mutableListOf(),

        @ManyToOne(fetch = FetchType.LAZY)
        var lecture: Lecture,

        @ManyToOne(fetch = FetchType.LAZY)
        var user: User
) {
    @Override
    override fun toString(): String {
        return ToStringBuilder
                .reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
    }
}
