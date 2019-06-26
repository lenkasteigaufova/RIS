package ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.util.Log
import app.repository
import com.example.lenkasteigaufova.restauracny_system_klient.R
import common.*
import common.extensions.formatted
import common.extensions.load
import common.extensions.log
import kotlinx.android.synthetic.main.cart.view.*
import kotlinx.android.synthetic.main.cart_item.view.*
import model.*
import ui.dayFood.FoodDayDetailActivity
import ui.drink.DrinkDetailActivity
import ui.food.FoodDetailActivity
import ui.profile.and
import ui.cart.CartItemType.*

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.cart)
    }
}

class CartView(context: Context, attrs: AttributeSet) : RxFrame(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()

        bind(repository.getUserProfile()){ profile ->

            ((profile.actualOrder.dayFoodsList.count()) +
                (profile.actualOrder.foodsList.count()) +
                (profile.actualOrder.drinksList.count())).log("--count ")

            val userOrder = profile.actualOrder

            cart_order_pay_button.setOnClickListener {  }
            cart_total_items_count.text = getTotalItemCountText(userOrder)
            cart_total_price_sum.text = getTotalCartPrice(userOrder)

            val items = userOrder.dayFoodsList.map(::DayFoodItem) and
                    userOrder.drinksList.map(::DrinkItem) and
                    userOrder.foodsList.map(::FoodItem)
            
            
            cart.init(R.layout.cart_item, items){ item ->
                cart_remove_food_item.setOnClickListener {
                    when (item) {
                        is CartItemType.FoodItem -> {
                            repository.deleteFoodFromCart(item.item.id)
                        }
                        is CartItemType.DayFoodItem -> {
                            repository.deleteDayFoodFromCart(item.item.id)
                        }
                        is CartItemType.DrinkItem -> {
                            repository.deleteDrinkFromCart(item.item.id)
                        }
                    }
                }


                cart_item_name.text = item.name
                cart_item_price_sum_food.text = item.price.formatted
                cart_item_description.text = item.description

                setOnClickListener {
                    val intent = when(item){
                        is CartItemType.FoodItem    -> Intent(context, FoodDetailActivity::class.java).apply {
                            putExtra("value", item.toJson())
                        }
                        is CartItemType.DayFoodItem -> Intent(context, FoodDayDetailActivity::class.java).apply {
                            putExtra("value", item.toJson())
                        }
                        is CartItemType.DrinkItem   -> Intent(context, DrinkDetailActivity::class.java).apply {
                            putExtra("value", item.toJson())
                        }
                    }
                    context.startActivity(intent)
                }

                when (item) {
                    is CartItemType.FoodItem -> { cart_item_photo.setImageDrawable(context.resources.getDrawable(R.drawable.food )) }
                    is CartItemType.DayFoodItem -> { cart_item_photo.setImageDrawable(context.resources.getDrawable(R.drawable.dayfood )) }
                    is CartItemType.DrinkItem -> { cart_item_photo.setImageDrawable(context.resources.getDrawable(R.drawable.drink )) }
                }
//                item.photo.let { cart_item_photo.load(it) }
            }
        }

    }

    private fun getTotalItemCountText(order: Order) = when (getTotalItemsCount(order)) {
        0       -> getTotalItemsCount(order).toString() + " položiek"
        1       -> getTotalItemsCount(order).toString() + " položka"
        in 2..4 -> getTotalItemsCount(order).toString() + " položky"
        else    -> getTotalItemsCount(order).toString() + " položiek"
    }

    private fun getTotalItemsCount(order: Order) =
        ((order.dayFoodsList.count()) + (order.drinksList.count()) + (order.foodsList.count()))

    private fun getTotalCartPrice(order: Order) : String {
        var result = 0F
        order.dayFoodsList.forEach { result += it.price }
        order.drinksList.forEach { result += it.price }
        order.foodsList.forEach { result += it.price }

//        val result = listOf(order.dayFoodsList?.map(DayFood::price), order.drinksList?.map(Drink::price), order.foodsList?.map(Food::price))
//                .filterNotNull()
//                .flatten()
//                .sumByDouble { it.toDouble() }
//                .toFloat()

        return (String.format("%.2f", result) + "€" )
    }

}

sealed class CartItemType(val name: String, val price: Float, val description: String, val photo: String){
    data class FoodItem(val item: Food):        CartItemType(item.name, item.price, item.description, item.name)
    data class DayFoodItem(val item: DayFood):  CartItemType(item.name, item.price, item.description, item.name)
    data class DrinkItem(val item: Drink):      CartItemType(item.name, item.price, item.description, item.name)
}
