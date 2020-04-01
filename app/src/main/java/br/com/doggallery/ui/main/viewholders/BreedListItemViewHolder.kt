package br.com.doggallery.ui.main.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.doggallery.R
import br.com.doggallery.util.GenericAdapter

class BreedListItemViewHolder : RecyclerView.ViewHolder, GenericAdapter.ViewBinder<String> {

    private var breedLabel: TextView
    private var rootView: View
    private var onItemClick: (String) -> Unit

    constructor(view: View, onItemClick: (breed: String) -> Unit) : super(view) {
        breedLabel = view.findViewById<View>(R.id.breedItemLabel) as TextView
        this.onItemClick = onItemClick
        this.rootView = view
    }

    override fun bindView(data: String) {
        breedLabel.text = data
       this.rootView.setOnClickListener { onItemClick(data)}
    }

}
