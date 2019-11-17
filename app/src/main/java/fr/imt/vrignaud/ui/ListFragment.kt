package fr.imt.vrignaud.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.imt.vrignaud.Book
import fr.imt.vrignaud.R

class ListFragment : Fragment() {

    private lateinit var bookViewModel: BookViewModel
    private lateinit var ctx: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = BookListAdapter(ctx)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(ctx)

        activity?.run {
            val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
            recyclerView.addItemDecoration(decoration)

            bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

            bookViewModel.allBooks.observe(this, Observer { books ->
                books?.let { adapter.setBooks(it) }
            })

            adapter.onItemClick = { book: Book ->
                bookViewModel.select(book)
            }

            bookViewModel.selected.observe(this, Observer { book ->
                adapter.setSelectedBook(book)
            })

            bookViewModel.dualPane.observe(this, Observer {
                adapter.setDualPane(it)
            })
        }

        return view
    }
}
