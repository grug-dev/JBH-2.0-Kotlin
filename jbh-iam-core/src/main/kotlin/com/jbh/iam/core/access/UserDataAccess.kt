package com.jbh.iam.core.access

import com.jbh.iam.core.model.UserCore
import com.jbh.iam.core.model.UserGroupCore
import org.springframework.stereotype.Component
import java.util.*

@Component
interface UserDataAccess {
    fun findByNickNameOrEmail(nickName: String, email: String): UserCore?

    fun findByUserOwnerId(id: UUID): UserGroupCore?
}