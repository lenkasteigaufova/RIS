package ui.onBoarding

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import com.example.lenkasteigaufova.restauracny_system_klient.R
import common.RxLayout
import common.extensions.start
import kotlinx.android.synthetic.main.registration.*

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.registration)

        toolbar_user_registration.setPadding(0, getStatusBarHeight(), 0, 0)
        toolbar_user_registration.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp)
        toolbar_user_registration.setNavigationOnClickListener { onBackPressed() }
        toolbar_user_registration.title = "História objednávok"
    }

    override fun onBackPressed() {
        super.onBackPressed()

        start<OnboardingActivity>()
        this.finish()
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

}

class Registration(context: Context, attrs: AttributeSet) : RxLayout(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()


    }
}
