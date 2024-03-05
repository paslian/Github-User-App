package com.example.navigationapi_pasliansahatrafael.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationapi_pasliansahatrafael.R
import com.example.navigationapi_pasliansahatrafael.data.remote.response.ItemsItem
import com.example.navigationapi_pasliansahatrafael.databinding.ActivityMainBinding
import com.example.navigationapi_pasliansahatrafael.setting.SettingPreferences
import com.example.navigationapi_pasliansahatrafael.setting.dataStore
import com.example.navigationapi_pasliansahatrafael.ui.adapter.ItemAdapter
import com.example.navigationapi_pasliansahatrafael.utils.ThemeViewModelFactory
import com.example.navigationapi_pasliansahatrafael.ui.viewmodel.MainViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref)).get(
            MainViewModel::class.java
        )
        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    val query = searchView.text.toString()
                    if (query.isNotEmpty()) {
                        mainViewModel.findItem(query)
                        searchView.hide()
                    } else {
                        mainViewModel.findItem("github")
                        searchView.hide()
                    }
                    true
                }
        }

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvGithubItem.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvGithubItem.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.githubItems.observe(this) { githubItems   ->
            setItemData(githubItems)
        }

        binding.fabListFavorite.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }

    }

    private fun setItemData(githubItems  : List<ItemsItem>) {
        val adapter = ItemAdapter(this)
        adapter.submitList(githubItems  )
        binding.rvGithubItem.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}