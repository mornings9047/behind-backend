package com.yourssu.behind.model.entity.comment

import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.user.User
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import org.springframework.data.jpa.repository.Temporal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Comment(

        @Id @GeneratedValue
        val id: Long? = null,

        @Lob
        var content: String,

        @Temporal
        val createdAt: LocalDateTime = LocalDateTime.now(),

        val deleteComment: Boolean = false,

        var postOwner: Boolean = false,

        @ManyToOne
        val parent: Comment?,

        @OneToMany(mappedBy = "parent")
        var children: MutableList<Comment> = mutableListOf<Comment>(),

        @ManyToOne
        val user: User,

        @ManyToOne
        val post: Post
) {
    @Override
    override fun toString(): String {
        return ToStringBuilder
                .reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
    }

    fun saveRecomment(reComment: Comment) {
        this.children.add(reComment)

    }
}
