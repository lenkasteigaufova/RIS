package ui.more

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import com.example.lenkasteigaufova.restauracny_system_klient.R
import common.RxLayout
import kotlinx.android.synthetic.main.alergens_list.*

class AlergensActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.alergens_list)

        toolbar_alergens_list.setPadding(0, getStatusBarHeight(), 0, 0)
        toolbar_alergens_list.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp)
        toolbar_alergens_list.setNavigationOnClickListener { finish() }
        toolbar_alergens_list.title = "Zoznam alergénov"
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

class AlergensList(context: Context, attrs: AttributeSet) : RxLayout(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()


    }
}
