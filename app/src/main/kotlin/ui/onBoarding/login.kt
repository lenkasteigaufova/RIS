package ui.onBoarding

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import com.example.lenkasteigaufova.restauracny_system_klient.R
import common.RxLayout
import common.extensions.start
import kotlinx.android.synthetic.main.login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.login)

        toolbar_user_login.setPadding(0, getStatusBarHeight(), 0, 0)
        toolbar_user_login.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp)
        toolbar_user_login.setNavigationOnClickListener { onBackPressed() }
        toolbar_user_login.title = "História objednávok"


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

class Login(context: Context, attrs: AttributeSet) : RxLayout(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()


    }
}

