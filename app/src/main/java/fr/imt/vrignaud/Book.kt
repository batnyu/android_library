package fr.imt.vrignaud

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey val isbn: String,
    val title: String,
    val price: Int,
    val cover: String,
    val synopsis: ArrayList<String>,
    val amount: Int = 0
)