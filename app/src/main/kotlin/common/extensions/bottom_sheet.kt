package common.extensions

import android.support.design.widget.BottomSheetBehavior
import android.view.View

inline fun <reified V: View> BottomSheetBehavior<V>.bottomCallback(crossinline slide: (V, Float) -> Unit, crossinline stateChanged: (V, Int) -> Unit){
    setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
        override fun onSlide(bottomSheet: View, slideOffset: Float)     = slide(bottomSheet as V, slideOffset)
        override fun onStateChanged(bottomSheet: View, newState: Int)   = stateChanged(bottomSheet as V, newState)
    })
}

inline fun <reified V: View> BottomSheetBehavior<V>.close(){
    state       = BottomSheetBehavior.STATE_COLLAPSED
    peekHeight  = 0
}

inline fun <reified V: View> BottomSheetBehavior<V>.open(){
    state       = BottomSheetBehavior.STATE_EXPANDED
}
