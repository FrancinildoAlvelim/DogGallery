package br.com.dogrepo.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class GenericAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: List<T> = emptyList()

    fun setList(listItems: List<T>) {
        this.items = listItems
        notifyDataSetChanged()
    }

    override fun getItemCount() = this.items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        getViewHolder(
            LayoutInflater.from(parent.context).inflate(viewType, parent, false),
            viewType
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as ViewBinder<T>).bindView(
            this.items[position]
        )

    abstract fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder

    protected abstract fun getLayoutId(position: Int, objects: T): Int

    override fun getItemViewType(position: Int): Int {
        return getLayoutId(position, this.items[position])
    }

    internal interface ViewBinder<T> {
        fun bindView(data: T)
    }
}