package morteza.darzi.SelfTeach

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout


abstract class BaseFragment : ScopedAppFragment() {

    abstract val title : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.title = title
    }

    fun errorTextChangeListner(v: TextInputLayout, errorMes : String) {
        v.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isEmpty()) {
                    v.isErrorEnabled = true
                    v.error = errorMes
                }
                if (s.isNotEmpty()) {
                    v.error = null
                    v.isErrorEnabled = false
                }

            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }
}