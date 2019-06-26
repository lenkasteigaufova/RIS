package ui.profile

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import app.repository
import common.RxLayout
import common.bind
import common.extensions.activity
import common.extensions.beVisibleIf
import common.extensions.load
import common.extensions.start
import kotlinx.android.synthetic.main.profile.view.*
import ui.onBoarding.OnboardingActivity


class ProfileView(context: Context, attrs: AttributeSet) : RxLayout(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()

        bind(repository.getUserProfile()) { profileData ->

            profile_user_name.text = profileData.name
            profile_user_mail.text = profileData.email
            profileData.photo.let { profile_photo.load(it)}

            user_orders_button.beVisibleIf(profileData.ordersList.isNotEmpty())

            user_orders_button.setOnClickListener {
                context.startActivity(Intent(context, UserOrdersHistoryActivity::class.java))
            }

            profile_logout.setOnClickListener {
                repository.logoutUser()
                start<OnboardingActivity>()
                activity.finish()
            }
        }


    }
}

infix fun <V> List<V>?.and(items: List<V>?) = (this ?: emptyList()) + (items ?: emptyList())
