package fr.imt.vrignaud.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.imt.vrignaud.Book
import fr.imt.vrignaud.R

class BookListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var books = emptyList<Book>() // Cached copy of books
    private var selectedBook: Book? = null
    private var dualPane: Boolean = false
    var onItemClick: ((Book) -> Unit)? = null

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: ConstraintLayout = itemView.findViewById(R.id.container)
        val bookItemView: TextView = itemView.findViewById(R.id.title)
        val bookImageView: ImageView = itemView.findViewById(R.id.cover)
        val priceItemView: TextView = itemView.findViewById(R.id.price)
        val amountItemView: TextView = itemView.findViewById(R.id.amount)
        val buttonDetails: ImageView = itemView.findViewById(R.id.buttonDetails)

        init {
            container.setOnClickListener {
                onItemClick?.invoke(books[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val context = holder.container.context
        val current = books[position]
        holder.bookItemView.text = current.title
        holder.container.background = ContextCompat.getDrawable(
            context,
            if (isSelectedBook(current)) R.drawable.selected else R.drawable.border_right
        )

        holder.buttonDetails.visibility =
            if (isSelectedBook(current)) View.INVISIBLE else View.VISIBLE

        val price =
            context.resources.getQuantityString(R.plurals.price, current.price, current.price)
        holder.priceItemView.text = price
        holder.amountItemView.text = if (current.amount > 0) current.amount.toString() else ""
        Picasso.get().load(current.cover).into(holder.bookImageView)

    }

    internal fun setBooks(books: List<Book>) {
        this.books = books
        notifyDataSetChanged()
    }

    internal fun setSelectedBook(book: Book) {
        this.selectedBook = book
        notifyDataSetChanged()
    }

    internal fun setDualPane(value: Boolean) {
        this.dualPane = value
    }

    override fun getItemCount() = books.size

    private fun isSelectedBook(current: Book) = current.isbn == selectedBook?.isbn && dualPane
}