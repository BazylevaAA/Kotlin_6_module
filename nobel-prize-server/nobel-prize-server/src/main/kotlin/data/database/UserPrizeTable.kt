package com.example.data.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object UserPrizeTable : Table("user_prizes") {
    val userId = reference("user_id", UserTable)
    val prizeId = reference("prize_id", PrizeTable)
    val addedAt = datetime("added_at").default(LocalDateTime.now())
    override val primaryKey = PrimaryKey(userId, prizeId)
}