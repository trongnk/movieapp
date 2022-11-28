package com.trongnk.movieapp.view.list

import androidx.lifecycle.*
import com.trongnk.movieapp.data.util.Constants
import com.trongnk.movieapp.domain.entity.MovieInfo
import com.trongnk.movieapp.domain.usecase.GetMovieList
import com.trongnk.movieapp.model.SortType
import com.trongnk.movieapp.model.ViewState
import com.trongnk.movieapp.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMovieList: GetMovieList,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _moviesState: MutableLiveData<ViewState<List<Movie>>> = MutableLiveData()
    val moviesState: LiveData<ViewState<List<Movie>>>
        get() = _moviesState

    val error: LiveData<Boolean> = Transformations.map(_moviesState) { it is ViewState.Failure }

    private var movies: List<MovieInfo> = listOf()
    private var sortType: SortType? = null

    init {
        loadMovieList()
    }

    fun setSortType(sortType: SortType) {
        this.sortType = sortType
        sort()
    }

    private fun sort() {
        movies = when(sortType) {
            SortType.TITLE -> movies.sortedWith(compareBy { it.title })
            SortType.RELEASED_DATE -> movies.sortedWith(compareByDescending { it.releaseDate })
            null -> movies
        }
        _moviesState.value = ViewState.Data(movies.map { it.toMovie() })
    }

    fun loadMovieList() {

        _moviesState.value = ViewState.Loading(null)

        viewModelScope.launch(dispatcherProvider.main()) {
            val result = withContext(dispatcherProvider.io()) {
                getMovieList.execute()
            }

            ensureActive()

            result.handle(doBefore = {
                Timber.d("Start getting data")
            }, onSuccess = {
                movies = it
                sort()
            }, onError = {
                _moviesState.value = ViewState.Failure(it)
            }, doAfter = {
                Timber.d("End getting data")
            })
        }
    }

    private fun MovieInfo.toMovie() = Movie(
        title = "$title (${releaseDate?.let{SimpleDateFormat(Constants.RELEASE_YEAR_FORMAT, Locale.US).format(it)} ?: ""})",
        originalTitle = title,
        description = "$duration - $genre",
        releasedDate = releaseDate?.let{SimpleDateFormat(Constants.RELEASE_DATE_FORMAT, Locale.US).format(it)} ?: "",
        poster = poster,
        onMyWatchList = watchList
    )

    data class Movie(
        val title: String,
        val originalTitle: String,
        val description: String,
        val onMyWatchList: Boolean,
        val releasedDate: String,
        val poster: String
    )
}