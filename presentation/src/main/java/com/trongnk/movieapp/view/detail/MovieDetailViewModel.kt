package com.trongnk.movieapp.view.detail

import androidx.lifecycle.*
import com.trongnk.movieapp.domain.entity.MovieInfo
import com.trongnk.movieapp.domain.usecase.AddMovieToWatchList
import com.trongnk.movieapp.domain.usecase.GetMovieDetail
import com.trongnk.movieapp.domain.usecase.RemoveMovieFromWatchList
import com.trongnk.movieapp.model.ViewState
import com.trongnk.movieapp.util.DispatcherProvider
import com.trongnk.movieapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetail: GetMovieDetail,
    private val addMovieToWatchList: AddMovieToWatchList,
    private val removeMovieFromWatchList: RemoveMovieFromWatchList,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _movieState: MutableLiveData<ViewState<MovieInfo>> = MutableLiveData()

    val movieData: LiveData<MovieInfo> = Transformations.map(_movieState) {
        if (it is ViewState.Data) {
            it.data
        } else null
    }

    private val _watchTrailerEvent = MutableLiveData<Event<String>>()
    val watchTrailerEvent: LiveData<Event<String>>
        get() = _watchTrailerEvent

    private var movieInfo: MovieInfo? = null

    fun watchTrailer() {
        movieInfo?.let {
            _watchTrailerEvent.value = Event(it.trailerLink)
        }
    }

    fun loadMovieDetail(title: String) {

        _movieState.value = ViewState.Loading(null)

        viewModelScope.launch(dispatcherProvider.main()) {
            val result = withContext(dispatcherProvider.io()) {
                getMovieDetail.execute(title)
            }

            ensureActive()

            result.handle(doBefore = {
                Timber.d("Start getting data")
            }, onSuccess = {
                movieInfo = it
                if (it != null) {
                    _movieState.value = ViewState.Data(it)
                }
            }, onError = {
                _movieState.value = ViewState.Failure(it)
            }, doAfter = {
                Timber.d("End getting data")
            })
        }
    }

    fun updateWatchList() {
        movieInfo?.let {
            if (!it.watchList) addToWatchList() else removeFromWatchList()
        }
    }

    private fun addToWatchList() {
        if (movieInfo == null) return

        viewModelScope.launch(dispatcherProvider.main()) {
            val result = withContext(dispatcherProvider.io()) {
                addMovieToWatchList.execute(movieInfo!!)
            }

            ensureActive()

            result.handle(onSuccess = {
                movieInfo = it
                _movieState.value = ViewState.Data(it)
            }, onError = {
                _movieState.value = ViewState.Failure(it)
            })
        }
    }

    private fun removeFromWatchList() {
        if (movieInfo == null) return

        viewModelScope.launch(dispatcherProvider.main()) {
            val result = withContext(dispatcherProvider.io()) {
                removeMovieFromWatchList.execute(movieInfo!!)
            }

            ensureActive()

            result.handle(onSuccess = {
                movieInfo = it
                _movieState.value = ViewState.Data(it)
            }, onError = {
                _movieState.value = ViewState.Failure(it)
            })
        }
    }
}