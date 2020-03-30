package br.com.dogrepo.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import br.com.dogrepo.R
import br.com.dogrepo.ui.main.fragments.BreedSelectFragmentDialog
import br.com.dogrepo.ui.main.fragments.MainFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbarMenu()
        setupMainFragment()

    }

    private fun setupMainFragment() {
        val newFragment = MainFragment()

        val transaction = supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, newFragment)
            disallowAddToBackStack()
        }
        transaction.commit();
    }

    private fun setupToolbarMenu() {
        setSupportActionBar(toolbar)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> showBreedSelectDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showBreedSelectDialog() {
        val breedDialog = BreedSelectFragmentDialog.newInstance()
        breedDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogSlideAnim)
        breedDialog.show(supportFragmentManager, BreedSelectFragmentDialog.tag())
    }

}
