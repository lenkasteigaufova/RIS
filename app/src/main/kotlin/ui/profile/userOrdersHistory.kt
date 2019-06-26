package ui.profile

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.View
import app.repository
import com.example.lenkasteigaufova.restauracny_system_klient.R
import common.RxLayout
import common.bind
import common.extensions.dateFormat
import common.extensions.formatted
import common.init
import kotlinx.android.synthetic.main.profile_order_item.view.*
import kotlinx.android.synthetic.main.profile_orders_item.view.*
import kotlinx.android.synthetic.main.profile_orders_list.*
import kotlinx.android.synthetic.main.profile_orders_list.view.*
import model.Order
import ui.cart.CartItemType.*


class UserOrdersHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.profile_orders_list)

        toolbar_user_orders.setPadding(0, getStatusBarHeight(), 0, 0)
        toolbar_user_orders.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp)
        toolbar_user_orders.setNavigationOnClickListener { finish() }
        toolbar_user_orders.title = "História objednávok"
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

class UserOrdersHistory(context: Context, attrs: AttributeSet) : RxLayout(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()

        bind(repository.getUserProfile()){ profile ->
            user_orders_history_recycler.init(R.layout.profile_orders_item, profile.ordersList, View::showUserOrdersHistory)
        }
    }
}

fun View.showUserOrdersHistory(userOrder: Order) {
    order_date.text     = userOrder.date.dateFormat
    order_price.text    = userOrder.sum.formatted

    val items = userOrder.dayFoodsList.map(::DayFoodItem) and
            userOrder.drinksList.map(::DrinkItem) and
            userOrder.foodsList.map(::FoodItem)

    orders_list.init(R.layout.profile_order_item, items){ item ->
        profile_order_item_count.text = "1x"
        profile_order_item_name.text = item.name
        profile_order_item_price.text = item.price.formatted
    }

}