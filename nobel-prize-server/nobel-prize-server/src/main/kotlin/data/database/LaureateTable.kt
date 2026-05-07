package com.example.data.database

import org.jetbrains.exposed.dao.id.IntIdTable

object LaureateTable : IntIdTable("laureates") {
    val prizeId = reference("prize_id", PrizeTable)
    val fullName = varchar("full_name", 255)
    val portion = varchar("portion", 50)
    val motivation = text("motivation")
    val portraitUrl = varchar("portrait_url", 500).nullable()
}