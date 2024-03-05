package com.example.navigationapi_pasliansahatrafael.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.navigationapi_pasliansahatrafael.R
import com.example.navigationapi_pasliansahatrafael.data.database.entity.FavoriteEntity
import com.example.navigationapi_pasliansahatrafael.ui.fragment.SectionsPagerAdapter
import com.example.navigationapi_pasliansahatrafael.databinding.ActivityDetailBinding
import com.example.navigationapi_pasliansahatrafael.ui.viewmodel.DetailViewModel
import com.example.navigationapi_pasliansahatrafael.utils.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.ln
import kotlin.math.pow

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var isFavorite: Boolean = false
    private var favorite: FavoriteEntity? = null


    private val viewModel by viewModels<DetailViewModel>(){
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val title = resources.getString(R.string.title_detail)
        supportActionBar?.title = title


        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        val username = intent.getStringExtra("USERNAME")

        viewModel.isLoading.observe(this, Observer {
            showLoading(it)
        })

        username?.let {
            viewModel.userDetail.observe(this, Observer { userDetail ->
                if (userDetail != null) {
                    checkRoom(username)
                    val noName = resources.getString(R.string.no_name)
                    val name = userDetail.name.takeIf { it?.isNotEmpty() == true } ?: noName
                    binding.tvItemName.text = name
                    binding.tvItemLogin.text = "@" + userDetail.login
                    Glide.with(this@DetailActivity)
                        .load(userDetail.avatarUrl)
                        .into(binding.imgItemPhoto)
                    val follower =  getFormatedNumber(userDetail.followers.toLong())
                    val following =  getFormatedNumber(userDetail.following.toLong())

                    binding.tvItemFollowers.text = "$follower ${resources.getString(R.string.followers_text)}"
                    binding.tvItemFollowing.text = "$following ${resources.getString(R.string.following_text)}"

                } else {
                    finish()
                }
            })
            viewModel.getDetailUser(username)
            viewModel.getFollowers(username)
            viewModel.getFollowing(username)
        } ?: finish()

        binding?.fabFavorite?.setOnClickListener {
            val loginText = binding.tvItemLogin.text.toString().trim()
            val username = loginText.removePrefix("@")
            checkRoom(username)
            if (isFavorite) {
                viewModel.deleteFromRoom(username)
                isFavorite = false
            } else {
                val data = FavoriteEntity()
                data.let {
                    it.username = username
                    it.avatarUrl = viewModel.userDetail.value?.avatarUrl ?: ""
                }
                viewModel.addToRoom(data)
                favorite = data
                isFavorite = true
            }
            setFavorite()
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun checkRoom(username: String) {
        viewModel.getGithubByUsername(username).observe(this, Observer { favoriteEntity ->
            if (favoriteEntity != null) {
                isFavorite = true
                favorite = favoriteEntity
                setFavorite()
            } else {
                isFavorite = false
                setFavorite()
            }
        })
    }

    private fun setFavorite() {
        if (isFavorite) {
            val favoriteDrawable = ContextCompat.getDrawable(this, R.drawable.baseline_favorite_24)
            binding.fabFavorite.setImageDrawable(favoriteDrawable)
        } else {
            val notFavoriteDrawable = ContextCompat.getDrawable(this, R.drawable.baseline_favorite_border_24)
            binding.fabFavorite.setImageDrawable(notFavoriteDrawable)
        }
    }


    fun getFormatedNumber(count: Long): String {
        if (count < 1000) return "" + count
        val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f %c", count / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

}