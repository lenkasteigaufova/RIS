package ui.dayFood

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
import kotlinx.android.synthetic.main.day_food_detail.*
import kotlinx.android.synthetic.main.day_food_detail.view.*
import model.DayFood

//ui.food.FoodDetailView

class FoodDayDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.day_food_detail)
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

class DayFoodDetailView(context: Context, attrs: AttributeSet) : RxLayout(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()

        val extras = (context as FoodDayDetailActivity).intent.extras.getString("value")
        val food = extras.jsonTo<DayFood>()

        day_food_detail_name.text = food.name
        day_food_price.text = String.format("%.2f", food.price) + "€"

        food.photo.let { day_food_detail_photo.load(it)}

        day_food_detail_add.setOnClickListener {
            repository.addDayFoodToCart(food)
        }

        bind(orderChanges.skip(1)) {
            Snackbar.make(this, "Pridanie prebehlo úspešne", Snackbar.LENGTH_SHORT).show()
        }
    }
}
