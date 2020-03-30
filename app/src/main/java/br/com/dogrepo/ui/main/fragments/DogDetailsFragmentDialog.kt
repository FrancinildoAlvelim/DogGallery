package br.com.dogrepo.ui.main.fragments

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import br.com.dogrepo.R
import br.com.dogrepo.util.extensions.loadImage
import kotlinx.android.synthetic.main.dog_detail_dialog.*

class DogDetailsFragmentDialog : DialogFragment() {

    companion object {
        fun newInstance() = DogDetailsFragmentDialog()
        fun tag() = "BreedSelectDialog"
    }

    private var dogImageUri: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.dog_detail_dialog, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showDogImage()
        closeDialogButton.setOnClickListener { dismiss() }
    }

    private fun showDogImage() {
        this.dogImageUri?.let {
            dogDetailImage.loadImage(it)
        }
    }

    override fun onResume() {
        prepareDialog()
        super.onResume()
    }

    override fun onDestroy() {
        activity?.window?.statusBarColor = ResourcesCompat.getColor(resources, R.color.white, null)
        super.onDestroy()
    }

    fun setDogImageUri(uri: String?) {
        this.dogImageUri = uri
    }

    private fun prepareDialog() {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)

        val windowHeight: Int = displayMetrics.heightPixels
        val windowWidth: Int = displayMetrics.widthPixels

        dialog!!.window!!.setLayout(
            windowWidth,
            windowHeight
        )
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.black)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = ResourcesCompat.getColor(resources, R.color.black, null)
    }
}