package ui.more

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import com.example.lenkasteigaufova.restauracny_system_klient.R
import common.RxFrame
import common.RxLayout
import kotlinx.android.synthetic.main.opening_hours.*

class OpeningHoursActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.opening_hours)

        toolbar_opening_hours.setPadding(0, getStatusBarHeight(), 0, 0)
        toolbar_opening_hours.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp)
        toolbar_opening_hours.setNavigationOnClickListener { finish() }
        toolbar_opening_hours.title = "Otváracie hodiny"
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

class OpeningHours(context: Context, attrs: AttributeSet) : RxFrame(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()


    }
}
