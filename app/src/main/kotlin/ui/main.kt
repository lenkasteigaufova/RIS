package ui

import android.content.Context
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.SparseArray
import com.example.lenkasteigaufova.restauracny_system_klient.R
import common.RxLayout
import kotlinx.android.synthetic.main.activity_main.view.*
import android.util.Log
import android.view.*
import kotlinx.android.synthetic.main.activity_main.*
import android.util.DisplayMetrics
import app.repository
import common.bind
import common.extensions.*
import kotlinx.android.synthetic.main.cart.view.*
import ui.more.AlergensActivity
import ui.more.OpeningHoursActivity
import ui.profile.ProfileLoginSwitcher


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.setPadding(0, getStatusBarHeight(), 0, 0)
    }

    fun convertPixelsToDp(px: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
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

class MainView(context: Context, attrs: AttributeSet) : RxLayout(context, attrs){

    override fun onFinishInflate() {
        super.onFinishInflate()

        main_fab.isIconAnimated = false
        main_fab_1.buttonSize = FloatingActionButton.SIZE_MINI
        main_fab_2.buttonSize = FloatingActionButton.SIZE_MINI
        main_fab_3.buttonSize = FloatingActionButton.SIZE_MINI
        main_fab_4.buttonSize = FloatingActionButton.SIZE_MINI
        main_fab_1.setOnClickListener {  } //poziadat o pomoc
        main_fab_2.setOnClickListener {  } //zaplatit ucet
        main_fab_3.setOnClickListener {  } //reklamacia
        main_fab_4.setOnClickListener {  } //udrzba stola

        toolbar.showMenu(R.menu.main_toolbar_menu) { item ->
            when(item.itemId){
                R.id.alergens -> { start<AlergensActivity>() }
                R.id.opening_hours -> { start<OpeningHoursActivity>() }
            }
        }

        main_bottom_toolbar.setOnClickListener {  }
        main_pager.adapter = MainAdapter(main_pager)
        main_tabs.setupWithViewPager(main_pager)
        setUpPagerIcons()


        main_tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabSelectedIconColor = ContextCompat.getColor(context, R.color.white)
                tab?.icon?.setColorFilter(tabSelectedIconColor, PorterDuff.Mode.SRC_IN)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabUnselectedIconColor = ContextCompat.getColor(context, R.color.grey_brown)
                tab?.icon?.setColorFilter(tabUnselectedIconColor, PorterDuff.Mode.SRC_IN)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) { }
        })

        bind(repository.getSession()) {

        }

        main_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) { }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                when(position + positionOffset){
//                    in 2f..3f -> {
//                        main_fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_add_circle_black_24dp))
//                        main_fab.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#004FFF"))
//                    }
//                }
                when (position) {
                    0 -> { toolbar.title = "Denné menu" }
                    1 -> { toolbar.title = "Nápoje" }
                    2 -> { toolbar.title = "Menu" }
                    3 -> { toolbar.title = "Profil" }
                    else -> { toolbar.title = "" }
                }
//                main_fab.show()
//                main_fab.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), PorterDuff.Mode.SRC_IN)
//                when(position){
//                    in 0..1 -> main_fab.hide()
//                    else -> main_fab.show()
//                }
            }
            override fun onPageSelected(position: Int) { }
        })
    }

    private fun setUpPagerIcons(){
        listOf(
            R.drawable.ic_room_service_white_24dp,
            R.drawable.ic_glass_tulip_grey_braun_24dp,
            R.drawable.ic_restaurant_grey_braun_24dp,
            R.drawable.ic_person_grey_braun_24dp
        ).forEachIndexed { i, icon -> main_tabs.getTabAt(i)?.icon = ContextCompat.getDrawable(context, icon).apply { mutate() } }
    }
}

class MainAdapter(val pager: MyPager) : PagerAdapter(){

    override fun isViewFromObject(view: View?, `object`: Any?) = view == `object`
    override fun getCount() = 4
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any?) = container.removeView(`object` as View)

    override fun instantiateItem(container: ViewGroup, position: Int): Any? {
        val view = LayoutInflater.from(container.context).inflate(when(position){
            0 -> R.layout.day_foods
            1 -> R.layout.drinks
            2 -> R.layout.foods
            3 -> R.layout.profile_login_switcher
            else -> throw RuntimeException("No such page")
        }, container, false)
        container.addView(view)
        pager.recoveryMyState(view)
        return view
    }
}

class MyPager(context: Context?, attrs: AttributeSet?) : ViewPager(context, attrs) {
    private var stateOfAllViews: SparseArray<Parcelable>? = null

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>?) {
        super.dispatchRestoreInstanceState(container)
        stateOfAllViews = container
    }

    fun recoveryMyState(view: View) {
        stateOfAllViews?.let(view::restoreHierarchyState)
        stateOfAllViews = null
    }
}