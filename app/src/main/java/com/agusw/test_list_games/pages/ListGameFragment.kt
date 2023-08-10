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
import com.agusw.test_list_games.databinding.FragmentListGameBinding
import com.agusw.test_list_games.rvAdapter.GameAdapter
import com.agusw.test_list_games.utils.LinearSpaceDecoration
import com.agusw.test_list_games.utils.textChanges
import com.agusw.test_list_games.vm.GameViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ListGameFragment : Fragment() {

    private lateinit var layout: FragmentListGameBinding
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentListGameBinding.inflate(inflater, container, false)
        .also { layout = it }
        .root

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout.rvGame.adapter = adapter
        layout.rvGame.addItemDecoration(
            LinearSpaceDecoration(
                space = (16 * Resources.getSystem().displayMetrics.density + .5f).toInt()
            )
        )

        layout.input.textChanges()
            .filterNot { it.isNullOrBlank() }
            .debounce(300)
            .distinctUntilChanged()
            .onEach { viewModel.search.value = it.toString() }
//            .onEach { viewModel.search.postValue(it.toString()) }
            .launchIn(lifecycleScope)

        lifecycleScope.launch {
            viewModel.gamePager.collectLatest(adapter::submitData)
        }

//        viewModel.search.observe(viewLifecycleOwner) {
//            lifecycleScope.launch {
//                viewModel.gamesPager(it).flow.collectLatest(adapter::submitData)
//            }
//        }
    }
}