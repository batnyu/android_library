package fr.imt.vrignaud.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.imt.vrignaud.Book
import fr.imt.vrignaud.R
import fr.imt.vrignaud.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var bookViewModel: BookViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)

        activity?.run {
            bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

//            bookViewModel.selected.observe(this, Observer<Book> { book ->
//                Log.d("SELECTED IN DETAIL", book.title)
//            })

            binding.lifecycleOwner = this
            binding.viewmodel = bookViewModel
        }

        return binding.root
    }
}
