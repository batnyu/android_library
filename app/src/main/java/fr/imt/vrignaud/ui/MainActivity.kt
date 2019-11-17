package fr.imt.vrignaud.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.widget.TextView
import fr.imt.vrignaud.R
import android.view.Menu
import android.view.MenuItem


class MainActivity : AppCompatActivity() {

    private lateinit var bookViewModel: BookViewModel
    lateinit var textCartItemCount: TextView
    private var selectedId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        val detailsFrame: View? = findViewById(R.id.details)
        val dualPane = detailsFrame?.visibility == View.VISIBLE

        bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        bookViewModel.setDualPane(dualPane)

        if (!dualPane) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    ListFragment(), ListFragment::class.java.name
                )
                .addToBackStack(ListFragment::class.java.name)
                .commit()
        }


        bookViewModel.selected.observe(this, Observer {
            if (!dualPane && selectedId != it.isbn) {
                Log.d("select", "WTF MAN")
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.container,
                        DetailFragment(), DetailFragment::class.java.name
                    )
                    .addToBackStack(DetailFragment::class.java.name)
                    .commit()
                selectedId = it.isbn
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val menuItem = menu.findItem(R.id.action_cart)

        val actionView = menuItem.actionView as View
        textCartItemCount = actionView.findViewById(R.id.lol_badge) as TextView

        bookViewModel.amountBooks.observe(this, Observer {
            setupBadge(it)
        })


        actionView.setOnClickListener { onOptionsItemSelected(menuItem) }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.getItemId()) {

            R.id.action_cart -> {
                // Do something
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupBadge(count: Int?) {

        if (count == null || count == 0) {
            if (textCartItemCount.getVisibility() != View.GONE) {
                textCartItemCount.setVisibility(View.GONE)
            }
        } else {
            textCartItemCount.setText(Math.min(count, 99).toString())
            if (textCartItemCount.getVisibility() != View.VISIBLE) {
                textCartItemCount.setVisibility(View.VISIBLE)
            }
        }
    }

    override fun onBackPressed() {
        selectedId = null
        super.onBackPressed()
    }
}
