package com.agusw.test_list_games.pages

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.agusw.test_list_games.R
import com.agusw.test_list_games.databinding.ActivityDetailBinding
import com.agusw.test_list_games.model.GameDetailResponse
import com.agusw.test_list_games.vm.GameViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val layout by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<GameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layout.root)
        setSupportActionBar(layout.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
            layout.toolbar.setNavigationOnClickListener { finish() }
        }

        viewModel.isFavorite.observe(this, Observer(this::setupFavorite))
        viewModel.detail.observe(this, Observer(this::setupData))

        val id = intent.getIntExtra("id", 0)
        if (id > 0)
            viewModel.getDetail(id)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private val iconFaved by lazy {
        ResourcesCompat.getDrawable(
            resources,
            R.drawable.baseline_favorite_24,
            null
        )
    }

    private val iconUnFaved by lazy {
        ResourcesCompat.getDrawable(
            resources,
            R.drawable.baseline_favorite_border_24,
            null
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val newValue = !item.isChecked
        viewModel.detail.value?.let { viewModel.toggleFavorite(newValue, it) }
        item.isChecked = newValue
        item.icon = if (newValue) iconFaved else iconUnFaved
        layout.toolbar.invalidateMenu()
        return super.onOptionsItemSelected(item)
    }

    private fun setupFavorite(value: Boolean) {
        layout.toolbar.menu.findItem(R.id.is_fav_menu)?.apply {
            isChecked = value
            icon = if (value) iconFaved else iconUnFaved

            layout.toolbar.invalidateMenu()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupData(data: GameDetailResponse) {
        Glide.with(this).load(data.image).fitCenter().into(layout.image)

        layout.publisher.text = data.publishers.firstOrNull()?.name ?: ""
        layout.title.text = data.name
        layout.released.text = "Release date ${data.released}"
        layout.rating.text = "${data.rating}"
        layout.played.text = "${data.playtime} played"
        layout.desc.text = Html.fromHtml(data.description, Html.FROM_HTML_MODE_LEGACY)
    }
}