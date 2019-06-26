package common.extensions

import android.support.v7.widget.Toolbar
import android.view.MenuItem

fun Toolbar.showMenu(menu: Int, action: (MenuItem) -> Unit) {
    this.menu.clear()
    this.inflateMenu(menu)
    this.setOnMenuItemClickListener { action(it).let { true } }
}
