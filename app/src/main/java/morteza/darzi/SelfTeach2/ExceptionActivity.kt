package morteza.darzi.SelfTeach2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ExceptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exception)

        val mes = findViewById<TextView>(R.id.message)
        val p = intent.getStringExtra(MyExceptionHandler.EXTRA_MY_EXCEPTION_HANDLER)
        mes.text = p
    }

}
