package com.navi.assignment.github.paging

import androidx.paging.*
import androidx.paging.LoadState.Loading
import androidx.paging.LoadState.NotLoading
import com.navi.assignment.github.paging.MergedState.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.scan

@OptIn(ExperimentalCoroutinesApi::class)
fun Flow<CombinedLoadStates>.asMergedLoadStates(): Flow<LoadStates> {
    val syncRemoteState = LoadStatesMerger()
    return scan(syncRemoteState.toLoadStates()) { _, combinedLoadStates ->
        syncRemoteState.updateFromCombinedLoadStates(combinedLoadStates)
        syncRemoteState.toLoadStates()
    }
}

/**
 * Track the combined [LoadState] of [RemoteMediator] and [PagingSource], so that each load type
 * is only set to [NotLoading] when [RemoteMediator] load is applied on presenter-side.
 */
private class LoadStatesMerger {
    var refresh: LoadState = NotLoading(endOfPaginationReached = false)
        private set
    var prepend: LoadState = NotLoading(endOfPaginationReached = false)
        private set
    var append: LoadState = NotLoading(endOfPaginationReached = false)
        private set
    var refreshState: MergedState = NOT_LOADING
        private set
    var prependState: MergedState = NOT_LOADING
        private set
    var appendState: MergedState = NOT_LOADING
        private set

    fun toLoadStates() = LoadStates(
            refresh = refresh,
            prepend = prepend,
            append = append
    )

    /**
     * For every new emission of [CombinedLoadStates] from the original [Flow], update the
     * [MergedState] of each [LoadType] and compute the new [LoadState].
     */
    fun updateFromCombinedLoadStates(combinedLoadStates: CombinedLoadStates) {
        computeNextLoadStateAndMergedState(
                sourceRefreshState = combinedLoadStates.source.refresh,
                sourceState = combinedLoadStates.source.refresh,
                remoteState = combinedLoadStates.mediator?.refresh,
                currentMergedState = refreshState,
        ).also {
            refresh = it.first
            refreshState = it.second
        }
        computeNextLoadStateAndMergedState(
                sourceRefreshState = combinedLoadStates.source.refresh,
                sourceState = combinedLoadStates.source.prepend,
                remoteState = combinedLoadStates.mediator?.prepend,
                currentMergedState = prependState,
        ).also {
            prepend = it.first
            prependState = it.second
        }
        computeNextLoadStateAndMergedState(
                sourceRefreshState = combinedLoadStates.source.refresh,
                sourceState = combinedLoadStates.source.append,
                remoteState = combinedLoadStates.mediator?.append,
                currentMergedState = appendState,
        ).also {
            append = it.first
            appendState = it.second
        }
    }

    /**
     * Compute which [LoadState] and [MergedState] to transition, given the previous and current
     * state for a particular [LoadType].
     */
    private fun computeNextLoadStateAndMergedState(
            sourceRefreshState: LoadState,
            sourceState: LoadState,
            remoteState: LoadState?,
            currentMergedState: MergedState,
    ): Pair<LoadState, MergedState> {
        if (remoteState == null) return sourceState to NOT_LOADING

        return when (currentMergedState) {
            NOT_LOADING -> when (remoteState) {
                is Loading -> Loading to REMOTE_STARTED
                is Error -> remoteState to REMOTE_ERROR
                else -> NotLoading(remoteState.endOfPaginationReached) to NOT_LOADING
            }
            REMOTE_STARTED -> when {
                remoteState is Error -> remoteState to REMOTE_ERROR
                sourceRefreshState is Loading -> Loading to SOURCE_LOADING
                else -> Loading to REMOTE_STARTED
            }
            REMOTE_ERROR -> when (remoteState) {
                is Error -> remoteState to REMOTE_ERROR
                else -> Loading to REMOTE_STARTED
            }
            SOURCE_LOADING -> when {
                sourceRefreshState is Error -> sourceRefreshState to SOURCE_ERROR
                remoteState is Error -> remoteState to REMOTE_ERROR
                sourceRefreshState is NotLoading -> {
                    NotLoading(remoteState.endOfPaginationReached) to NOT_LOADING
                }
                else -> Loading to SOURCE_LOADING
            }
            SOURCE_ERROR -> when (sourceRefreshState) {
                is Error -> sourceRefreshState to SOURCE_ERROR
                else -> sourceRefreshState to SOURCE_LOADING
            }
        }
    }
}
