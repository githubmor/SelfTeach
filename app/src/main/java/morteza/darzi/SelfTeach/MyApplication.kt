package morteza.darzi.SelfTeach

import android.app.Application
import com.activeandroid.ActiveAndroid


/**
 * Created by M on 14/12/31.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ActiveAndroid.initialize(this)

    }
}
