package com.example.homework5.ui.fragments

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.homework5.data.db.entities.RatedMoviesEntity
import com.example.homework5.data.mappings.MovieMapping
import com.example.homework5.databinding.FragmentBottomSheetBinding
import com.example.homework5.helpers.CurrentUser
import com.example.homework5.helpers.ServiceLocator
import com.example.homework5.ui.items.MovieItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailsBottomSheetFragment(
    private val movie: MovieItem,
    private val updateAdapters: (List<MovieItem>, List<MovieItem>) -> Unit,
) : BottomSheetDialogFragment() {
    private var viewBindingBS: FragmentBottomSheetBinding? = null
    private var dbInstance = ServiceLocator.getDbInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBindingBS = FragmentBottomSheetBinding.inflate(inflater)
        return viewBindingBS?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBindingBS?.apply {
            nameTv.text = movie.name
            descriptionTv.text = movie.description
            yearTv.text = movie.releaseDate.toString()
            rateTv.text = movie.rating.toString()

            rateBtn.setOnClickListener {
                movie.userRating = sb.progress
                lifecycleScope.launch {
                    async(Dispatchers.IO) {
                        try {
                            dbInstance.ratedMoviesDao.addRate(
                                RatedMoviesEntity(
                                    userId = CurrentUser.getCurrentUserId(),
                                    movieId = movie.id,
                                    rating = movie.userRating
                                )
                            )
                        } catch (e: SQLiteConstraintException) {
                            dbInstance.ratedMoviesDao.updateRate(
                                userId = CurrentUser.getCurrentUserId(),
                                movieId = movie.id,
                                rating = movie.userRating
                            )
                        }
                    }.await()

                    async(Dispatchers.IO) {
                        dbInstance.movieDao.updateAvgRating(movieId = movie.id)
                    }.await()


                    val newFavList = mutableListOf<MovieItem>()
                    val favModelList = (async(Dispatchers.IO) {
                        dbInstance.favoriteMovieDao.getFavoriteMoviesForUser(CurrentUser.getCurrentUserId())
                    }.await())
                    favModelList?.forEach {
                        newFavList.add(MovieMapping.toItem(it))
                    }

                    val newList = mutableListOf<MovieItem>()
                    (async(Dispatchers.IO) {
                        dbInstance.movieDao.getAllMovies()
                    }.await())?.forEach {
                        newList.add(MovieMapping.toItem(it, favModelList!!))
                        if (it.movieId == movie.id) rateTv.text = it.rating.toString()
                    }


                    updateAdapters(newList, newFavList)
                }

            }
        }
    }

    companion object {
        const val FRAGMENT_TAG = "Bottom sheet fragment"

        fun newInstance(
            movie: MovieItem,
            updateAdapters: (List<MovieItem>, List<MovieItem>) -> Unit
        ): DetailsBottomSheetFragment {
            return DetailsBottomSheetFragment(movie, updateAdapters)
        }
    }
}