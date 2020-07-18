package com.yourssu.behind.model.professor

import com.yourssu.behind.model.lecture.Lecture
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Professor(
        @Id @GeneratedValue val id: Long? = null,

        val name: String,

        @OneToMany(mappedBy = "professor")
        var letcures : MutableList<Lecture> = mutableListOf<Lecture>()
        ) {
}