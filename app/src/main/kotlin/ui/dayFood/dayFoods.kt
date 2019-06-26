package ui.dayFood

import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import app.orderChanges
import app.repository
import com.example.lenkasteigaufova.restauracny_system_klient.R
import common.*
import common.extensions.formatted
import common.extensions.load
import common.extensions.log
import common.extensions.toast
import kotlinx.android.synthetic.main.day_foods.view.*
import kotlinx.android.synthetic.main.food_item_day_foods.view.*
import model.DayFood


class DayFoodsView(context: Context, attrs: AttributeSet) : RxLayout(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()

        day_foods_refresh.apply {
            setColorSchemeResources(R.color.grey_brown)
            setOnRefreshListener {
                repository.fetchDailyMeals()
            }
        }

        bind(repository.observeDailyMeals()) { foodsState ->
            when (foodsState) {
                is State.Initial -> repository.fetchDailyMeals()
                is State.Success -> showDailyMeals(foodsState.value)
                is State.Failure -> toast("Error occured ${foodsState.error}")
            }

            day_foods_refresh.isRefreshing = foodsState is State.Loading
        }
    }

    private fun showDailyMeals(items: List<DayFood>) {
            foods_day.init(R.layout.food_item_day_foods, items){ dayFoodItem ->
                food_item_day_foods_name.text = dayFoodItem.name.capitalize()
                food_item_day_foods_price.text = dayFoodItem.price.formatted

                setOnClickListener {
                    val intent = Intent(context, FoodDayDetailActivity::class.java)
                    intent.putExtra("value", dayFoodItem.toJson())
                    context.startActivity(intent)
                }

                add_to_cart_day_foods.setOnClickListener {
                    repository.addDayFoodToCart(dayFoodItem)
                }

                dayFoodItem.photo.let { food_item_day_foods_photo.load(it) }
            }
    }

}

