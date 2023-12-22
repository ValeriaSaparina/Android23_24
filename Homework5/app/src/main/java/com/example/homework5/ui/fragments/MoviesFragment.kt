package com.example.homework5.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework5.R
import com.example.homework5.data.db.entities.FavoriteMovieEntity
import com.example.homework5.data.db.entities.MovieEntity
import com.example.homework5.data.mappings.MovieMapping
import com.example.homework5.data.models.MovieModel
import com.example.homework5.databinding.FragmentMoviesBinding
import com.example.homework5.helpers.CurrentUser
import com.example.homework5.helpers.FilterTypes
import com.example.homework5.helpers.Params
import com.example.homework5.helpers.ServiceLocator
import com.example.homework5.ui.adapters.FavoriteMovieAdapter
import com.example.homework5.ui.adapters.HorizontalTouchItem
import com.example.homework5.ui.adapters.MovieItemAdapter
import com.example.homework5.ui.items.MovieItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MoviesFragment : Fragment() {
    private var viewBinding: FragmentMoviesBinding? = null
    private val dbInstance = ServiceLocator.getDbInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMoviesBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = activity?.findViewById<Toolbar>(R.id.my_toolbar)
        toolbar?.apply {
            findViewById<Button>(R.id.profile_btn).setOnClickListener {
                parentFragmentManager.beginTransaction().replace(
                    Params.containerViewId,
                    ProfileFragment.newInstance(),
                    ProfileFragment.FRAGMENT_TAG
                )
                    .addToBackStack(null).commit()
            }
        }


        val movieItemList = mutableListOf<MovieItem>()
        val favoriteMovieItemList = mutableListOf<MovieItem>()

        viewBinding?.apply {

            lifecycleScope.launch {
                val allMoviesFromDB = async(Dispatchers.IO) {
                    dbInstance.movieDao.getMoviesByReleaseYearDesc()
                }.await()
                val favoritesMovies = async(Dispatchers.IO) {
                    dbInstance.favoriteMovieDao.getFavoriteMoviesForUser(CurrentUser.getCurrentUser().userId)
                }.await()
                initListeners(favoritesMovies)
                allMoviesFromDB.forEach {
                    movieItemList.add(
                        MovieItem(
                            id = it.movieId,
                            name = it.name,
                            description = it.description,
                            isFav = favoritesMovies?.contains(MovieMapping.toModel(it))
                                ?: false,
                            rating = it.rating,
                            releaseDate = it.releaseDate
                        )
                    )
                }

                favoritesMovies!!.forEach {
                    favoriteMovieItemList.add(
                        MovieItem(
                            id = it.movieId,
                            name = it.name,
                            description = it.description,
                            isFav = true,
                            rating = it.rating
                        )
                    )
                }

                msgTv.visibility = if (movieItemList.isEmpty()) View.VISIBLE else View.GONE

                val movieItemAdapter = MovieItemAdapter(
                    movieItemList,
                    requireContext(),
                    onButtonClicked = ::onButtonClicked,
                    onItemClicked = ::onItemClicked
                )

                val favoriteMovieAdapter = FavoriteMovieAdapter(
                    favoriteMovieItemList,
                    requireContext(),
                    ::onFavButtonClicked,
                    onItemClicked = ::onItemClicked

                )


                val callback: ItemTouchHelper.Callback = HorizontalTouchItem(
                    onItemSwiped = ::onItemSwiped
                )
                val touchHelper = ItemTouchHelper(callback)
                touchHelper.attachToRecyclerView(allMoviesRv)
                allMoviesRv.adapter = movieItemAdapter
                allMoviesRv.layoutManager = GridLayoutManager(context, 2)
                favoritesRv.adapter = favoriteMovieAdapter
                favoritesRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }

    }

    private fun onItemClicked(movieItem: MovieItem) {
        val bottomSheet = DetailsBottomSheetFragment.newInstance(movieItem, ::updateAdapters)
        bottomSheet.show(parentFragmentManager, DetailsBottomSheetFragment.FRAGMENT_TAG)
    }

    private fun updateAdapters(newList: List<MovieItem>, newFavList: List<MovieItem>) {
        viewBinding?.apply {
            val allMovieAdapter = allMoviesRv.adapter as MovieItemAdapter
            val favMovieAdapter = favoritesRv.adapter as FavoriteMovieAdapter
            allMovieAdapter.updateItems(newList)
            favMovieAdapter.updateItems(newFavList)
        }
    }

    private fun onItemSwiped(movieItem: MovieItem) {
        viewBinding?.apply {
            val adapterAllMovies = allMoviesRv.adapter as MovieItemAdapter
            lifecycleScope.launch {
                val newEntityList = async(Dispatchers.IO) {
                    dbInstance.movieDao.deleteMovie(
                        MovieEntity(
                            movieId = movieItem.id,
                            name = movieItem.name,
                            description = movieItem.description,
                            rating = movieItem.rating,
                            releaseDate = movieItem.releaseDate
                        )
                    )
                    dbInstance.movieDao.getAllMovies()
                }.await()
                val newMovieItemList = mutableListOf<MovieItem>()
                newEntityList!!.forEach {
                    newMovieItemList.add(
                        MovieItem(
                            id = it.movieId,
                            name = it.name,
                            description = it.description
                        )
                    )
                }

                adapterAllMovies.updateItems(newMovieItemList)

                msgTv.visibility = if (newEntityList.isEmpty()) View.VISIBLE else View.GONE

                val favoritesMovies = async(Dispatchers.IO) {
                    dbInstance.favoriteMovieDao.getFavoriteMoviesForUser(CurrentUser.getCurrentUser().userId)
                }.await()

                val newFavMoviesList = mutableListOf<MovieItem>()

                favoritesMovies!!.forEach {
                    newFavMoviesList.add(
                        MovieItem(
                            id = it.movieId,
                            name = it.name,
                            description = it.description,
                            isFav = true
                        )
                    )
                }
                val favMoviesAdapter = viewBinding?.favoritesRv?.adapter as FavoriteMovieAdapter
                favMoviesAdapter.updateItems(newFavMoviesList)

            }
        }
    }

    private fun initListeners(movieItemList: List<MovieModel>?) {
        viewBinding?.apply {
            fabAddMovie.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(
                        Params.containerViewId,
                        AddMovieFragment.newInstance(),
                        AddMovieFragment.FRAGMENT_TAG
                    )
                    .addToBackStack(null)
                    .commit()
            }

            filterSpn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val adapter = allMoviesRv.adapter as MovieItemAdapter
                    val sortedMovieList = mutableListOf<MovieItem>()
                    lifecycleScope.launch {
                        async(Dispatchers.IO) {
                            when (position) {
                                FilterTypes.YEAR_ASC.getValue() -> {
                                    dbInstance.movieDao.getMoviesByReleaseYearAsc().forEach {
                                        sortedMovieList.add(
                                            MovieItem(
                                                id = it.movieId,
                                                name = it.name,
                                                description = it.description,
                                                isFav = movieItemList?.contains(
                                                    MovieMapping.toModel(
                                                        it
                                                    )
                                                )
                                                    ?: false
                                            )
                                        )
                                    }
                                }

                                FilterTypes.YEAR_DESC.getValue() -> {
                                    dbInstance.movieDao.getMoviesByReleaseYearDesc().forEach {
                                        sortedMovieList.add(
                                            MovieItem(
                                                id = it.movieId,
                                                name = it.name,
                                                description = it.description,
                                                isFav = movieItemList?.contains(
                                                    MovieMapping.toModel(
                                                        it
                                                    )
                                                )
                                                    ?: false
                                            )
                                        )
                                    }
                                }

                                FilterTypes.RATING_ASC.getValue() -> {
                                    dbInstance.movieDao.getMoviesByRatingAsc().forEach {
                                        sortedMovieList.add(
                                            MovieItem(
                                                id = it.movieId,
                                                name = it.name,
                                                description = it.description,
                                                isFav = movieItemList?.contains(
                                                    MovieMapping.toModel(
                                                        it
                                                    )
                                                )
                                                    ?: false
                                            )
                                        )
                                    }
                                }

                                FilterTypes.RATING_DESC.getValue() -> {
                                    dbInstance.movieDao.getMoviesByRatingDesc().forEach {
                                        sortedMovieList.add(
                                            MovieItem(
                                                id = it.movieId,
                                                name = it.name,
                                                description = it.description,
                                                isFav = movieItemList?.contains(
                                                    MovieMapping.toModel(
                                                        it
                                                    )
                                                )
                                                    ?: false
                                            )
                                        )
                                    }
                                }
                            }
                        }.await()
                    }
                    adapter.updateItems(sortedMovieList)
                }

            }

        }
    }

    private fun onButtonClicked(movieItem: MovieItem) {
        lifecycleScope.launch {
            if (movieItem.isFav) {
                movieItem.isFav = false
                async(Dispatchers.IO) {
                    dbInstance.favoriteMovieDao.deleteFavoriteMovie(
                        FavoriteMovieEntity(
                            CurrentUser.getCurrentUser().userId,
                            movieItem.id
                        )
                    )
                }.await()
            } else {
                movieItem.isFav = true
                async(Dispatchers.IO) {
                    dbInstance.favoriteMovieDao.addFavoriteMovie(
                        FavoriteMovieEntity(
                            CurrentUser.getCurrentUser().userId,
                            movieItem.id
                        )
                    )
                }.await()
            }
            val favoritesMovies = async(Dispatchers.IO) {
                dbInstance.favoriteMovieDao.getFavoriteMoviesForUser(CurrentUser.getCurrentUser().userId)
            }.await()

            val newFavMoviesList = mutableListOf<MovieItem>()

            favoritesMovies!!.forEach {
                newFavMoviesList.add(
                    MovieItem(
                        id = it.movieId,
                        name = it.name,
                        description = it.description,
                        isFav = true
                    )
                )
            }
            val favMoviesAdapter = viewBinding?.favoritesRv?.adapter as FavoriteMovieAdapter
            favMoviesAdapter.updateItems(newFavMoviesList)
        }
    }

    private fun onFavButtonClicked(movieItem: MovieItem) {
        lifecycleScope.launch {
            movieItem.isFav = false
            async(Dispatchers.IO) {
                dbInstance.favoriteMovieDao.deleteFavoriteMovie(
                    FavoriteMovieEntity(
                        CurrentUser.getCurrentUser().userId,
                        movieItem.id
                    )
                )
            }.await()


            viewBinding?.apply {
                val movieItemAdapter = allMoviesRv.adapter as MovieItemAdapter
                val favMoviesAdapter = favoritesRv.adapter as FavoriteMovieAdapter

                val newFavMoviesList = mutableListOf<MovieItem>()
                val favoritesMovies = async(Dispatchers.IO) {
                    dbInstance.favoriteMovieDao.getFavoriteMoviesForUser(CurrentUser.getCurrentUser().userId)
                }.await()
                favoritesMovies!!.forEach {
                    newFavMoviesList.add(
                        MovieItem(
                            id = it.movieId,
                            name = it.name,
                            description = it.description,
                            releaseDate = it.releaseDate,
                            rating = it.rating,
                            isFav = true
                        )
                    )
                }

                val allMoviesFromDB = async(Dispatchers.IO) {
                    dbInstance.movieDao.getAllMovies()
                }.await()
                val movieItemList = mutableListOf<MovieItem>()
                allMoviesFromDB!!.forEach {
                    movieItemList.add(
                        MovieItem(
                            id = it.movieId,
                            name = it.name,
                            description = it.description,
                            isFav = favoritesMovies.contains(MovieMapping.toModel(it))
                        )
                    )
                }

                movieItemAdapter.updateItems(movieItemList)
                favMoviesAdapter.updateItems(newFavMoviesList)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    companion object {
        const val FRAGMENT_TAG = "movie fragment"

        fun newInstance(): MoviesFragment {
            return MoviesFragment()
        }
    }
}