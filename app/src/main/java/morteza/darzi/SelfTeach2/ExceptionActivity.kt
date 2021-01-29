package morteza.darzi.SelfTeach2

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import morteza.darzi.SelfTeach2.databinding.ActivityExceptionBinding


class ExceptionActivity : AppCompatActivity() {

    private lateinit var activityExceptionBinding: ActivityExceptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityExceptionBinding = ActivityExceptionBinding.inflate(layoutInflater)
        setContentView(activityExceptionBinding.root)

        val mes = findViewById<TextView>(R.id.message)
        val p = intent.getStringExtra(MyExceptionHandler.EXTRA_MY_EXCEPTION_HANDLER)
        mes.text = p

        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        emailIntent.type = "plain/text"
        emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("pc2man@gmail.com"))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "خطای زمان اجرا")
        emailIntent.putExtra(Intent.EXTRA_TEXT,p)
        startActivity(emailIntent)
    }

}
