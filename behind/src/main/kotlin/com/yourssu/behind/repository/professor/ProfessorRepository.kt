package com.yourssu.behind.repository.professor

import com.yourssu.behind.model.entity.professor.Professor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfessorRepository : JpaRepository<Professor, Long>{
    fun findByNameContains(name: String): List<Professor>
}
