package fr.imt.vrignaud

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.StringBuilder

class BookRepository(private val service: BookService, private val bookDao: BookDao) {
    suspend fun allBooks(): LiveData<List<Book>> {
        refreshBooks()
        return bookDao.getBooks()
    }

    fun amountBooks(): LiveData<Int> {
        return bookDao.getSumAmount()
    }

    fun getTotalPrice(): LiveData<Int> {
        return bookDao.getTotalPrice()
    }

    private suspend fun refreshBooks() {
        val booksCount = bookDao.getBooksCount()
        if (booksCount == 0) {
            bookDao.insertMany(service.getBooks())
        }
    }

    suspend fun update(book: Book) {
        bookDao.update(book)
    }

    suspend fun getCommercialOffers(): List<Offer> {
        val books = bookDao.getAllBooks()
        val list = mutableListOf<String>()
        books.forEach {
            for (x in 0 until it.amount) list.add(it.isbn)
        }
        if(list.count() == 0) return emptyList()
        return service.getCommercialOffers(list.joinToString(separator = ",")).offers
    }
}