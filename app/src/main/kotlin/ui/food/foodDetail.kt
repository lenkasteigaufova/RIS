package ui.food

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import app.orderChanges
import app.repository
import com.example.lenkasteigaufova.restauracny_system_klient.R
import common.RxLayout
import common.bind
import common.extensions.load
import common.extensions.log
import common.jsonTo
import kotlinx.android.synthetic.main.food_detail.*
import kotlinx.android.synthetic.main.food_detail.view.*
import model.Food


class FoodDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.food_detail)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { finish() }

        toolbar.setPadding(0, getStatusBarHeight(), 0, 0)
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

class FoodDetailView(context: Context, attrs: AttributeSet) : RxLayout(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()

        val extras = (context as FoodDetailActivity).intent.extras.getString("value")
        val food = extras.jsonTo<Food>()

        food_detail_name.text = food.name
        food_detail_price.text = String.format("%.2f", food.price) + "€"

        food.photo.let { food_detail_photo.load(it)}

        food_detail_add.setOnClickListener {
            repository.addFoodToCart(food)
        }

        bind(orderChanges.skip(1)) {
            Snackbar.make(this, "Pridanie prebehlo úspešne", Snackbar.LENGTH_SHORT).show()
        }

    }
}