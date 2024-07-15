package com.jbh.iam.core.facade

import java.util.*

data class UserRole(
    val id: UUID? = null,
    var name: RoleName = RoleName.ROLE_USER
)