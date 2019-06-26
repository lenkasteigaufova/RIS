package ui.food

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.widget.Toast
import app.repository
import com.example.lenkasteigaufova.restauracny_system_klient.R
import common.*
import common.extensions.load
import common.extensions.log
import common.extensions.formatted
import kotlinx.android.synthetic.main.food_item.view.*
import kotlinx.android.synthetic.main.foods.view.*
import model.Food


class FoodsView(context: Context, attrs: AttributeSet) : RxLayout(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()

        foods_refresh.apply {
            setColorSchemeResources(R.color.grey_brown)
            setOnRefreshListener {
                repository.fetchFoods()
            }
        }

        bind(repository.observeFoods()){ foodsState ->
            foodsState.log("Foods state")
            when(foodsState){
                is State.Initial -> repository.fetchFoods()
                is State.Success -> showFoods(foodsState.value)
                is State.Failure -> Toast.makeText(context, "Error occured ${foodsState.error}", Toast.LENGTH_SHORT).show()
            }

            foods_refresh.apply {
                isRefreshing    = foodsState is State.Loading
            }

        }
    }

    private fun showFoods(foodsItems: List<Food>) {
        foods.init(R.layout.food_item, foodsItems){ item ->
            food_item_name.text = item.name.capitalize()
            food_item_price.text = item.price.formatted

            setOnClickListener {
                val intent = Intent(context, FoodDetailActivity::class.java)
                intent.putExtra("value", item.toJson())
                context.startActivity(intent)
            }

            add_to_cart_food.setOnClickListener {
                repository.addFoodToCart(item)
            }

            item.photo.also(food_item_photo::load) //?: food_image.setImageResource(R.drawable.food1)

        }
    }
}
