package morteza.darzi.SelfTeach2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity based Exception handler ...
 */
class MyExceptionHandler(private val context: AppCompatActivity) : Thread.UncaughtExceptionHandler {
    private val rootHandler: Thread.UncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()

    init {
        // we should store the current exception handler -- to invoke it for all not handled exceptions ...
        // we replace the exception handler now with us -- we will properly dispatch the exceptions ...
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (ex is RuntimeException) {
            // note we can't just open in Android an dialog etc. we have to use Intents here
            // http://stackoverflow.com/questions/13416879/show-a-dialog-in-thread-setdefaultuncaughtexceptionhandler

            val registerActivity = Intent(context, ExceptionActivity::class.java)
            registerActivity.putExtra(EXTRA_MY_EXCEPTION_HANDLER, ex.message)
            //registerActivity.putExtra("ad",ex.message)
            registerActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            context.startActivity(registerActivity)
            // make sure we die, otherwise the app will hang ...
            android.os.Process.killProcess(android.os.Process.myPid())
            // sometimes on older android version killProcess wasn't enough -- strategy pattern should be considered here
            System.exit(0)
        } else {
            rootHandler.uncaughtException(thread, ex)
        }
    }

    companion object {

        const val EXTRA_MY_EXCEPTION_HANDLER = "EXTRA_MY_EXCEPTION_HANDLER"
    }
}
