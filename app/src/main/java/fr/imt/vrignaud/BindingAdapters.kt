package fr.imt.vrignaud

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("app:joinToString")
fun joinToString(view: TextView, strings: List<String>?) {
    view.text = strings?.joinToString(separator = "\n\n") ?: ""
}

@BindingAdapter("app:showIfNull")
fun showIfNull(view: TextView, book: Book?) {
    view.visibility = if (book == null) View.VISIBLE else View.GONE
}

@BindingAdapter("app:hideIfNull")
fun hideIfNull(view: View, book: Book?) {
    view.visibility = if (book == null) View.GONE else View.VISIBLE
}

@BindingAdapter("app:hideIfNullOrNoAmount")
fun hideIfNullOrNoAmount(view: View, book: Book?) {
    view.visibility = if (book == null || book.amount == 0) View.GONE else View.VISIBLE
}

@BindingAdapter("imageUrl", "error")
fun loadImage(view: ImageView, url: String?, error: Drawable) {
    if (url != null)
        Picasso.get().load(url).error(error).into(view)
}

@BindingAdapter("app:intToString")
fun intToString(view: TextView, int: Int) {
    val context = view.context
    val price = context.resources.getQuantityString(R.plurals.price, int, int)
    view.text = price
}