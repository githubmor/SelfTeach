package Unused

//package morteza.darzi.SelfTeach
//
//
//import BL.Book_Old
//import BL.MyException
//import android.content.Intent
//import android.os.Bundle
//import android.view.Menu
//import android.view.MenuItem
//import android.widget.EditText
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//
///**
// * Created by M on 14/12/28.
// */
//class Add_Book : AppCompatActivity() {
//
//    private var type: EditText? = null
//    private var pagecount: EditText? = null
//    private var bookOld: Book_Old? = null
//    internal var free: Boolean = false
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.add_book)
//
//        val i = intent
//        free = i.getBooleanExtra("free", false)
//
//
//        //save = (Button) findViewById(R.id.saveBook);
//        type = findViewById(R.id.book_name)
//        pagecount = findViewById(R.id.book_pagecount)
//
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.addbookmenu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        when (item.itemId) {
//            R.id.savingBook -> try {
//                bookOld = Book_Old(type!!.text.toString(), Integer.parseInt(pagecount!!.text.toString()), free)
//                bookOld!!.save()
//                //dBhandeler.InsertBook(bookOld);
//                Toast.makeText(applicationContext, "کتاب " + bookOld!!.type + " ذخیره شد", Toast.LENGTH_SHORT).show()
//                val intent = Intent(applicationContext, Booking::class.java)
//                intent.putExtra("free", free)
//                startActivity(intent)
//                finish()
//            } catch (e: MyException) {
//                Toast.makeText(applicationContext, e.message.toString(), Toast.LENGTH_SHORT).show()
//            }
//
//            else -> {
//            }
//        }
//
//        return super.onOptionsItemSelected(item)
//
//    }
//}