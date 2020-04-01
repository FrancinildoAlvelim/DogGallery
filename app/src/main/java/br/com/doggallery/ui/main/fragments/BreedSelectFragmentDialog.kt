package br.com.doggallery.ui.main.fragments

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.doggallery.R
import br.com.doggallery.ui.main.state.MainStateEvent
import br.com.doggallery.ui.main.state.MainStateEvent.GetDogsByBreed
import br.com.doggallery.ui.main.viewholders.BreedListItemViewHolder
import br.com.doggallery.ui.main.viewmodels.MainViewModel
import br.com.doggallery.util.GenericAdapter
import kotlinx.android.synthetic.main.breed_select_dialog.*
import org.koin.android.ext.android.inject


class BreedSelectFragmentDialog : DialogFragment() {

    companion object {
        fun newInstance() = BreedSelectFragmentDialog()
        fun tag() = "BreedSelectDialog"
    }

    lateinit var breedListAdapter: GenericAdapter<String>

    private val mainViewModel: MainViewModel by inject()

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
        val state = mainViewModel.getViewValue()
        if (state?.breedList.isNullOrEmpty()) {
            mainViewModel.dispatchEvent(MainStateEvent.GetAllBreeds())
        }
    }

    private fun setupViewModel() {
        mainViewModel.dataState.observe(viewLifecycleOwner,
            Observer { dataState ->
                dataState?.data.let { state ->
                    state?.breedList?.let {
                        mainViewModel.setBreedList(it)
                    }
                    state?.selectedBreed?.let {
                        mainViewModel.setSelectedBreed(it)
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

    private fun notifyLoading() =
        Toast.makeText(context, "Searching dogs", Toast.LENGTH_SHORT).show()

    private fun setupBreedList() {
        breedListAdapter = object : GenericAdapter<String>() {
            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder =
                BreedListItemViewHolder(view) { this@BreedSelectFragmentDialog.onBreedSelected(it) }

            override fun getLayoutId(position: Int, objects: String): Int =
                R.layout.breed_select_dialog_item
        }

        val layoutManager = LinearLayoutManager(context)
        breedSelectRecyclerView.layoutManager = layoutManager
        breedSelectRecyclerView.setHasFixedSize(true)
        breedSelectRecyclerView.adapter = breedListAdapter
    }

    private fun onBreedSelected(breed: String?) {
        if (breed == mainViewModel.getViewValue()?.selectedBreed) return dismiss() //already selected breed

        breed?.let {
            mainViewModel.dispatchEvent(MainStateEvent.SetDogBreed(breed))
            mainViewModel.dispatchEvent(GetDogsByBreed(breed))
            dismiss()
        } ?: throw Exception("Please provide an breed to search")
    }

    private fun setupCloseButton() {
        closeBreedDialogButton.setOnClickListener { dismiss() }
    }

    private fun prepareDialog() {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)

        val windowHeight: Int = displayMetrics.heightPixels
        val windowWidth: Int = displayMetrics.widthPixels
        val dialogHeightInPercentage = (windowHeight * 0.72).toInt()


        dialog?.window?.apply {
            setLayout(
                windowWidth,
                dialogHeightInPercentage
            )
            setGravity(Gravity.BOTTOM)
            setBackgroundDrawableResource(android.R.color.transparent)
        }

        dialog!!.window!!.attributes.windowAnimations = R.style.DialogSlideAnim
    }

    override fun onResume() {
        prepareDialog()
        super.onResume()
    }

}