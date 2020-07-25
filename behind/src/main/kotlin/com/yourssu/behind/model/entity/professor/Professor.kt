package com.yourssu.behind.model.entity.professor

import com.yourssu.behind.model.entity.lecture.Lecture
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Professor(
        @Id @GeneratedValue val id: Long? = null,

        val name: String,

        @OneToMany(mappedBy = "professor")
        var letcures: MutableList<Lecture> = mutableListOf<Lecture>()
) {
    @Override
    override fun toString(): String {
        return ToStringBuilder
                .reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}