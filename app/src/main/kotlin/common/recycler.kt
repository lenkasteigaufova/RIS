package common

import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import common.extensions.inflate

inline fun <T> RecyclerView.init(@LayoutRes itemLayout: Int, data: List<T> = emptyList(), crossinline map: View.(T) -> Unit) {
    if(layoutManager == null)
        layoutManager = LinearLayoutManager(context)

    val adptr = object : EasyAdapter() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = object: RecyclerView.ViewHolder(parent.inflate(viewType)) {}
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = holder.itemView.map(data[position])
        override fun getItemCount(): Int = data.size
        override fun getItemViewType(position: Int) = itemLayout
    }

    if(adapter != null)
        swapAdapter(adptr, false)
    else
        adapter = adptr
}

typealias EasyAdapter   = RecyclerView.Adapter<RecyclerView.ViewHolder>