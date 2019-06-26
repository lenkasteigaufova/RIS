package ui.drink

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import app.repository
import com.example.lenkasteigaufova.restauracny_system_klient.R
import common.*
import common.extensions.formatted
import common.extensions.load
import common.extensions.toast
import kotlinx.android.synthetic.main.drink_item.view.*
import kotlinx.android.synthetic.main.drinks.view.*
import model.Drink


class DrinksView(context: Context, attrs: AttributeSet) : RxLayout(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()

        drinks_refresh.apply {
            setColorSchemeResources(R.color.grey_brown)
            setOnRefreshListener {
                repository.fetchDrinks()
            }
        }

        bind(repository.observeDrinks()) { state ->
            when (state) {
                is State.Initial -> repository.fetchDrinks()
                is State.Success -> showDrinks(state.value)
                is State.Failure -> toast("Error occured ${state.error}")
            }

            drinks_refresh.isRefreshing = state is State.Loading


        }
    }

    private fun showDrinks(items: List<Drink>) {
            drinks.init(R.layout.drink_item, items) { item ->
                 drink_item_name.text = item.name.capitalize()
                drink_item_price.text = item.price.formatted

                setOnClickListener {
                    val intent = Intent(context, DrinkDetailActivity::class.java)
                    intent.putExtra("value", item.toJson())
                    context.startActivity(intent)
                }

                add_to_cart_drink.setOnClickListener {
                    repository.addDrinkToCart(item)
                }

                item.photo.let { drink_item_photo.load(it)} //?: food_image.setImageResource(R.drawable.food1)
            }
    }

}
