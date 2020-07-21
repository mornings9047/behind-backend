package com.yourssu.behind.repository.user

import com.yourssu.behind.model.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun existsBySchoolId(schoolId: String): Boolean
    fun findBySchoolId(schoolId: String): Optional<User>
}
