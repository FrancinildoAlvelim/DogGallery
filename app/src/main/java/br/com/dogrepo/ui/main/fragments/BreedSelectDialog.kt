package br.com.dogrepo.ui.main.fragments

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.dogrepo.R
import br.com.dogrepo.ui.main.state.MainStateEvent
import br.com.dogrepo.ui.main.state.MainStateEvent.GetDogsByBreed
import br.com.dogrepo.ui.main.viewholders.BreedListItemViewHolder
import br.com.dogrepo.ui.main.viewmodels.MainViewModel
import br.com.dogrepo.util.GenericAdapter
import kotlinx.android.synthetic.main.breed_select_dialog.*


class BreedSelectDialog : DialogFragment() {

    companion object {
        fun newInstance() = BreedSelectDialog()
        fun tag() = "BreedSelectDialog"
    }

    lateinit var mainViewModel: MainViewModel
    lateinit var breedListAdapter: GenericAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.breed_select_dialog, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCloseButton()
        setupViewModel()
        setupBreedList()
        getBreedsFromApi()
    }

    private fun getBreedsFromApi() {
        mainViewModel.dispatchEvent(MainStateEvent.GetAllBreeds())
    }

    private fun setupViewModel() {
        mainViewModel = activity?.let { ViewModelProvider(this).get(MainViewModel::class.java) }
            ?: throw Exception("Invalid activity")

        mainViewModel.dataState.observe(viewLifecycleOwner,
            Observer { dataState ->
                dataState?.data.let { state ->
                    state?.breedList?.let {
                        mainViewModel.setBreedList(it)
                    }
                }
                dataState?.message?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
                dataState?.loading?.let { isLoading ->
                    if (isLoading)
                        notifyLoading()
                    else
                        hideLoading()
                }

            })
        mainViewModel.viewState.observe(viewLifecycleOwner,
            Observer { state ->
                state.let {
                    state.breedList?.let {
                        this.addBreedList(it)
                    }
                }
            }
        )
    }

    private fun addBreedList(breedList: List<String>) {
        breedListAdapter.setList(breedList)
    }

    private fun hideLoading() {
        breedsProgressBar.visibility = View.GONE
    }

    private fun notifyLoading() {
        Toast.makeText(context, "Buscando dados", Toast.LENGTH_SHORT).show()
    }


    private fun setupBreedList() {
        breedListAdapter = object : GenericAdapter<String>() {
            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder =
                BreedListItemViewHolder(view) { this@BreedSelectDialog.onBreedSelected(it) }

            override fun getLayoutId(position: Int, objects: String): Int =
                R.layout.breed_select_dialog_item
        }

        val layoutManager = LinearLayoutManager(context)
        breedSelectRecyclerView.layoutManager = layoutManager
        breedSelectRecyclerView.setHasFixedSize(true)
        breedSelectRecyclerView.adapter = breedListAdapter
    }

    private fun onBreedSelected(breed: String) {
        breed.let {
            mainViewModel.dispatchEvent(GetDogsByBreed(breed))
        }
    }

    private fun setupCloseButton() {
        closeBreedDialogButton.setOnClickListener { dismiss() }
    }

    override fun onResume() {
        prepareDialog()
        super.onResume()
    }

    private fun prepareDialog() {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)

        val windowHeight: Int = displayMetrics.heightPixels
        val windowWidth: Int = displayMetrics.widthPixels
        val dialogHeightInPercentage = (windowHeight * 0.72).toInt()


        dialog!!.window!!.setLayout(
            windowWidth,
            dialogHeightInPercentage
        )
        dialog!!.window!!.setGravity(Gravity.BOTTOM)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent);
        dialog!!.window!!.attributes.windowAnimations = R.style.DialogSlideAnim
    }

}