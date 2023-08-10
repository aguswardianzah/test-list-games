package com.agusw.test_list_games.pages

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.agusw.test_list_games.databinding.FragmentFavGameBinding
import com.agusw.test_list_games.rvAdapter.GameAdapter
import com.agusw.test_list_games.utils.LinearSpaceDecoration
import com.agusw.test_list_games.vm.GameViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavGameFragment : Fragment() {

    private lateinit var layout: FragmentFavGameBinding
    private val viewModel by activityViewModels<GameViewModel>()
    private val adapter by lazy {
        GameAdapter(Glide.with(this)) { g ->
            activity?.let {
                startActivity(Intent(it, DetailActivity::class.java).apply {
                    putExtras(Bundle().apply { putInt("id", g.gid) })
                })
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentFavGameBinding.inflate(inflater, container, false)
        .also { layout = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout.rvGame.adapter = adapter
        layout.rvGame.addItemDecoration(
            LinearSpaceDecoration(
                space = (16 * Resources.getSystem().displayMetrics.density + .5f).toInt()
            )
        )

        lifecycleScope.launch {
            viewModel.favPager.collectLatest(adapter::submitData)
        }
    }
}