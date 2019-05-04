package morteza.darzi.SelfTeach

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View

import com.activeandroid.query.Select
import com.gc.materialdesign.views.ButtonFloat

import BL.Book
import DBAdapter.Book_Adapter

/**
 * Created by M on 14/11/23.
 */
class Booking : AppCompatActivity() {

    internal var free: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking)

        val i = intent
        free = i.getBooleanExtra("free", false)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (free)
            supportActionBar!!.setTitle("مطالعه آزاد")
        else
            supportActionBar!!.setTitle("لیست کتاب های ترم")


        val buttonFloat = findViewById<View>(R.id.addBook_Float) as ButtonFloat
        val res = resources
        buttonFloat.drawableIcon = res.getDrawable(R.drawable.ic_add_white_48dp)
        buttonFloat.setOnClickListener {
            val intent = Intent(applicationContext, Add_Book::class.java)
            intent.putExtra("free", free)
            startActivity(intent)
        }

        val mRecyclerView = findViewById<View>(R.id.BookList) as RecyclerView

        val books: MutableList<Book>
        if (free)
            books = Select().from(Book::class.java).where("free = " + 1).execute()
        else
            books = Select().from(Book::class.java).where("free = " + 0).execute()

        mRecyclerView.setHasFixedSize(true)

        // use a linear layout manager
        val mLayoutManager = LinearLayoutManager(applicationContext)
        mRecyclerView.layoutManager = mLayoutManager

        // specify an adapter (see also next example)

        val mAdapter = Book_Adapter(applicationContext, books)
        mRecyclerView.adapter = mAdapter

        //        mRecyclerView.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                Toast.makeText(getApplicationContext(),)
        //            }
        //        });


    }


}
