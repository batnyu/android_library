package fr.imt.vrignaud

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDao {

    @Query("SELECT * from book_table")
    fun getBooks(): LiveData<List<Book>>

    @Query("SELECT SUM(amount) from book_table")
    fun getSumAmount(): LiveData<Int>

    @Query("SELECT SUM(amount*price) from book_table")
    fun getTotalPrice(): LiveData<Int>

    @Query("SELECT COUNT(*) from book_table")
    suspend fun getBooksCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: Book)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMany(books: List<Book>)

    @Update
    suspend fun update(book: Book)

    @Query("DELETE FROM book_table")
    suspend fun deleteAllBooks()

    @Query("SELECT * from book_table")
    suspend fun getAllBooks(): List<Book>
}