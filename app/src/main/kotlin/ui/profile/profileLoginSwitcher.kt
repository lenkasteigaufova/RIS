package ui.profile

import android.content.Context
import android.util.AttributeSet
import app.repository
import common.RxLayout
import common.bind
import common.extensions.beGone
import common.extensions.beVisible
import kotlinx.android.synthetic.main.login_main.view.*
import kotlinx.android.synthetic.main.profile.view.*

class ProfileLoginSwitcher(context: Context, attrs: AttributeSet) : RxLayout(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()

        bind(repository.getSession()) {
            if (it.email == "") {
                profile_root.beGone()
                login_main_root.beVisible()
            } else {
                profile_root.beVisible()
                login_main_root.beGone()
            }
        }

    }
}

class LoginMain(context: Context, attrs: AttributeSet) : RxLayout(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()

        main_login_button.setOnClickListener {
            repository.loginUser()
        }

    }
}
