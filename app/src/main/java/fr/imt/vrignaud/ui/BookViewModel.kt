package fr.imt.vrignaud.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import fr.imt.vrignaud.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter.
class BookViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: BookRepository
    // LiveData gives us updated books when they change.
    var allBooks: LiveData<List<Book>>
    var amountBooks: LiveData<Int>
    var totalPrice: LiveData<Int>
    val selected = MutableLiveData<Book>()
    val dualPane = MutableLiveData<Boolean>()
    val offers = MutableLiveData<List<Offer>>()

    init {
        // Gets reference to BookDao from BookRoomDatabase to construct
        // the correct BookRepository.
        val booksDao = BookRoomDatabase.getDatabase(
            application,
            viewModelScope
        ).bookDao()
        repository =
            BookRepository(BookService.create(), booksDao)

        allBooks = liveData(Dispatchers.IO) {
            val books = repository.allBooks()
            emitSource(books)
        }

        amountBooks = repository.amountBooks()
        totalPrice = repository.getTotalPrice()
    }

    fun select(book: Book) {
        selected.value = book
    }

    fun setDualPane(value: Boolean) {
        dualPane.value = value
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
/*    fun insert(book: Book) = viewModelScope.launch {
        repository.insert(book)
    }*/

    fun update(book: Book) = viewModelScope.launch {
        val updatedAmount = book.amount + 1
        val copiedBook = book.copy(amount = updatedAmount)
        select(copiedBook)
        repository.update(copiedBook)
    }

    fun removeFromCart(book: Book) = viewModelScope.launch {
        val copiedBook = book.copy(amount = 0)
        select(copiedBook)
        repository.update(copiedBook)
    }

    fun getCommercialOffers() = viewModelScope.launch {
        offers.value = repository.getCommercialOffers()
    }
}