package com.example.navigationapi_pasliansahatrafael.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationapi_pasliansahatrafael.R
import com.example.navigationapi_pasliansahatrafael.data.remote.response.ItemsItem
import com.example.navigationapi_pasliansahatrafael.databinding.ActivityFavoriteBinding
import com.example.navigationapi_pasliansahatrafael.helper.mappingHelper
import com.example.navigationapi_pasliansahatrafael.ui.adapter.FavoriteAdapter
import com.example.navigationapi_pasliansahatrafael.ui.viewmodel.FavoriteViewModel
import com.example.navigationapi_pasliansahatrafael.utils.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>(){
        FavoriteViewModelFactory.getInstance(application)
    }
    private val list = ArrayList<ItemsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        x
        supportActionBar?.title = title

        favoriteViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        favoriteViewModel.getAllGithub().observe(this) { githubItems   ->
            if (githubItems.count() > 0){
                list.clear()
                val listUser = mappingHelper.mapCursorToListUser(githubItems)
                list.addAll(listUser)
            } else {
                list.clear()
                binding.noData.visibility = View.VISIBLE
            }
            setItemData(list)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setItemData(githubItems  : List<ItemsItem>) {
        val layoutManager = LinearLayoutManager(this)
        binding.rvGithubItem.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvGithubItem.addItemDecoration(itemDecoration)

        val adapter = FavoriteAdapter(this)
        adapter.submitList(githubItems)
        binding.rvGithubItem.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}