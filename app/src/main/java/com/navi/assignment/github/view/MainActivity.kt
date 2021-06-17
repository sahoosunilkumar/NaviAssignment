package com.navi.assignment.github.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.navi.assignment.github.GlideApp
import com.navi.assignment.github.ServiceLocator
import com.navi.assignment.github.databinding.ActivityMainBinding
import com.navi.assignment.github.model.PullState
import com.navi.assignment.github.paging.asMergedLoadStates
import com.navi.assignment.github.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
        private set

    private val viewModel: MainViewModel by viewModels {
        object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
            ): T {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(ServiceLocator.instance()
            .getRepository()) as T
            }
        }
    }

    private lateinit var adapter: PullRequestListingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        binding.searchBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.getPullRequests(binding.input.text.toString(), binding.repoEt.text.toString(), PullState.CLOSED).collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        adapter = PullRequestListingAdapter(glide)
        binding.list.adapter = adapter.withLoadStateHeaderAndFooter(
                header = RequestLoadStateAdapter(adapter),
                footer = RequestLoadStateAdapter(adapter)
        )

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                    .asMergedLoadStates()
                    .distinctUntilChangedBy { it.refresh }
                    .filter { it.refresh is LoadState.NotLoading }
                    .collect { binding.list.scrollToPosition(0) }
        }
    }

}
