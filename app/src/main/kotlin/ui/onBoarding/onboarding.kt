package ui.onBoarding

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import com.example.lenkasteigaufova.restauracny_system_klient.R
import common.RxLayout
import common.extensions.activity
import common.extensions.start
import kotlinx.android.synthetic.main.onboarding.view.*
import ui.MainActivity

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.onboarding)
    }
}

class OnboardingView(context: Context, attrs: AttributeSet) : RxLayout(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()

        login_button.setOnClickListener {
            start<LoginActivity>()
            activity.finish()
        }
        without_login_button.setOnClickListener {
            start<MainActivity>()
            activity.finish()
        }
        registration_button.setOnClickListener {
            start<RegistrationActivity>()
            activity.finish()
        }
    }
}
