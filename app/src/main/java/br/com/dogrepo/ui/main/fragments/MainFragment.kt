package br.com.dogrepo.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
import br.com.dogrepo.R
import br.com.dogrepo.ui.main.state.MainStateEvent.GetDogsRandomly
import br.com.dogrepo.ui.main.viewholders.DogListItemViewHolder
import br.com.dogrepo.ui.main.viewmodels.MainViewModel
import br.com.dogrepo.util.GenericAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {
    private lateinit var dogListAdapter: GenericAdapter<String>
    private val itemsPerColumn: Int = 2
    private val mainViewModel: MainViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = LayoutInflater.from(context).inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupDogAdapter()
        setupPullToRefresh()
        setupViewModel()
        getRandomDogs()
    }

    private fun setupPullToRefresh() {
        pullToRefresh.setOnRefreshListener {
            getRandomDogs()
        }
    }

    private fun getRandomDogs() {
        mainViewModel.dispatchEvent(GetDogsRandomly())
    }

    private fun setupViewModel() {

        mainViewModel.dataState.observe(viewLifecycleOwner,
            Observer { state ->
                state?.data?.let { dataState ->
                    dataState.dogList?.let {
                        mainViewModel.setDogList(it)
                    }

                }
                state?.message?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
                state?.loading?.let {
                    if (it) {
                        notifyLoading()
                    } else {
                        notifySuccess()
                    }
                }
            }
        )

        mainViewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.let {
                viewState.dogList?.let {
                    this.setDogList(it)
                }
            }
        })

    }

    private fun notifyLoading() {
    }

    private fun notifySuccess() = hideLoading()

    private fun hideLoading() {
        if (pullToRefresh.isRefreshing) {
            pullToRefresh?.isRefreshing = false
        }
    }

    private fun setDogList(list: List<String>) {
        dogListAdapter.setList(list)
    }

    private fun showDogDetails(dogImage: String?) {
        val dogDetailDialog = DogDetailsFragmentDialog.newInstance()
        dogDetailDialog.setDogImageUri(dogImage)
        activity?.supportFragmentManager?.let {
            dogDetailDialog.show(
                it,
                DogDetailsFragmentDialog.tag()
            )
        }
    }

    private fun setupDogAdapter() {
        dogListAdapter = object : GenericAdapter<String>() {
            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder =
                DogListItemViewHolder(view) { showDogDetails(it) }

            override fun getLayoutId(position: Int, objects: String): Int =
                R.layout.dog_list_item_layout
        }

        val staggeredLayoutManager =
            StaggeredGridLayoutManager(itemsPerColumn, StaggeredGridLayoutManager.VERTICAL)

        staggeredLayoutManager.apply {
            gapStrategy = GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        }

        dogListRecyclerView.apply {
            layoutManager = staggeredLayoutManager
            adapter = dogListAdapter
            setHasFixedSize(true)
        }
    }
}