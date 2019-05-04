package morteza.darzi.SelfTeach

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class Splash : AppCompatActivity() {

    /** Called when the activity is first created.  */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val _splashTime = 4000
        Handler().postDelayed(object : Thread() {
            override fun run() {
                val mainMenu = Intent(this@Splash, DashboardActivity::class.java)
                this@Splash.startActivity(mainMenu)
                this@Splash.finish()
                overridePendingTransition(R.drawable.fadein, R.drawable.fadeout)
            }
        }, _splashTime.toLong())
    }
}