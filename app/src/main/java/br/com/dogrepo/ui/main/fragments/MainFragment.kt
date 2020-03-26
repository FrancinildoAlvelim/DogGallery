package br.com.dogrepo.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.dogrepo.R
import br.com.dogrepo.models.Dog
import br.com.dogrepo.ui.main.viewholders.DogListItemViewHolder
import br.com.dogrepo.util.GenericAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    lateinit var dogListAdapter: GenericAdapter<Dog>
    private val itemsPerColumn: Int = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = LayoutInflater.from(context).inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      setupDogAdapter()
    }

    private fun setupDogAdapter() {
        dogListAdapter = object : GenericAdapter<Dog>() {
            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder  = DogListItemViewHolder(view)
            override fun getLayoutId(position: Int, objects: Dog): Int  = R.layout.dog_list_item_layout
        }
        val layoutManager = StaggeredGridLayoutManager(itemsPerColumn, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        dogListRecyclerView.layoutManager = layoutManager
        dogListRecyclerView.setHasFixedSize(true)
        dogListRecyclerView.adapter = dogListAdapter

        val dogList = listOf(
            "https://images.dog.ceo/breeds/clumber/n02101556_5030.jpg",
            "https://images.dog.ceo/breeds/maltese/n02085936_21320.jpg",
            "https://images.dog.ceo/breeds/appenzeller/n02107908_1176.jpg",
            "https://images.dog.ceo/breeds/terrier-lakeland/n02095570_4226.jpg",
            "https://images.dog.ceo/breeds/terrier-irish/n02093991_3673.jpg",
            "https://images.dog.ceo/breeds/lhasa/n02098413_7571.jpg",
            "https://images.dog.ceo/breeds/hound-ibizan/n02091244_4086.jpg",
            "https://images.dog.ceo/breeds/mastiff-english/3.jpg",
            "https://images.dog.ceo/breeds/mountain-swiss/n02107574_2436.jpg",
            "https://images.dog.ceo/breeds/komondor/n02105505_2420.jpg"
        ).map {
            Dog(picture = it)
        }
        dogListAdapter.setList(dogList)
    }
}