package com.example.data.repository

import com.example.data.database.UserTable
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserRepositoryImpl : UserRepository {

    private fun ResultRow.toUser() = User(
        id = this[UserTable.id].value,
        username = this[UserTable.username],
        passwordHash = this[UserTable.passwordHash],
        role = this[UserTable.role]
    )

    override suspend fun findByUsername(username: String): User? =
        newSuspendedTransaction {
            UserTable.selectAll()
                .where { UserTable.username eq username }
                .map { it.toUser() }
                .firstOrNull()
        }

    override suspend fun findById(id: Int): User? =
        newSuspendedTransaction {
            UserTable.selectAll()
                .where { UserTable.id eq id }
                .map { it.toUser() }
                .firstOrNull()
        }

    override suspend fun createUser(username: String, passwordHash: String, role: String): User =
        newSuspendedTransaction {
            val insertedId = UserTable.insert {
                it[UserTable.username] = username
                it[UserTable.passwordHash] = passwordHash
                it[UserTable.role] = role
            }[UserTable.id].value
            User(insertedId, username, passwordHash, role)
        }
}