package fr.imt.vrignaud.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fr.imt.vrignaud.Offer
import fr.imt.vrignaud.R
import java.lang.Exception

class CartActivity : AppCompatActivity() {

    private lateinit var bookViewModel: BookViewModel
    private var totalPrice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        bookViewModel.getCommercialOffers()

        bookViewModel.offers.observe(this, Observer {
            getBestOffer(it)
        })

        bookViewModel.totalPrice.observe(this, Observer {
            totalPrice = it

        })
    }

    fun getBestOffer(offers: List<Offer>) {

        val finalPrices = offers.map {
            when (it.type) {
                "percentage" -> totalPrice - totalPrice * it.value / 100
                "minus" -> totalPrice - it.value
                "slice" -> totalPrice - (totalPrice / it.sliceValue) * it.value
                else -> throw Exception("not supported offer")
            }
        }

        val yes = finalPrices.min()
        findViewById<TextView>(R.id.totalPrice).text = totalPrice.toString()
        findViewById<TextView>(R.id.totalPriceWithReduc).text = yes.toString()
    }
}
