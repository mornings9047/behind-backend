package com.yourssu.behind.repository

import com.yourssu.behind.model.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun existsBySchoolId(schoolId: String): Boolean
    fun findBySchoolId(schoolId: String): User
}
