package com.example.homework5.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.homework5.helpers.Helper
import com.example.homework5.helpers.Params
import com.example.homework5.databinding.FragmentAddMovieBinding
import com.example.homework5.data.db.entities.MovieEntity
import com.example.homework5.helpers.ServiceLocator
import com.example.homework5.exceptions.EmptyFieldsException
import com.example.homework5.exceptions.InvalidInputData
import com.example.homework5.exceptions.NonUniqueField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddMovieFragment : Fragment() {

    private var viewBinding: FragmentAddMovieBinding? = null
    private val dbInstance = ServiceLocator.getDbInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddMovieBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()

    }

    private fun initListener() {
        viewBinding?.apply {
            addMovieBtn.setOnClickListener {

                val name = titleEt.text.toString()
                val releaseDate = (releaseDateEt.text.toString())
                val description = descriptionEt.text.toString()

                try {
                    if (name.isEmpty() || description.isEmpty() || releaseDate.isEmpty()) throw EmptyFieldsException()
                    Helper.isValidReleaseDate(releaseDate.toInt())
                    var sameMovie: MovieEntity?
                    lifecycleScope.launch {
                        try {
                            sameMovie = withContext(Dispatchers.IO) {
                                dbInstance.movieDao.getMovieByNameAndReleaseDate(
                                    name = name,
                                    releaseDate = releaseDate.toInt()
                                )
                            }
                            if (sameMovie != null) throw NonUniqueField(Params.NON_UNIQUE_MOVIE)
                            launch(Dispatchers.IO) {
                                dbInstance.movieDao.addMovie(
                                    MovieEntity(
                                        movieId = 0,
                                        name = name,
                                        description = description,
                                        rating = 0.0,
                                        releaseDate = releaseDate.toInt()
                                    )
                                )
                            }
                            Helper.showNotification(context, Params.MOVIE_WAS_ADDED)
                        } catch (e: NonUniqueField) {
                            Helper.showNotification(context, e.message)
                        }
                    }
                } catch (e: EmptyFieldsException) {
                    Helper.showNotification(context, e.message)
                } catch (e: InvalidInputData) {
                    Helper.showNotification(context, e.message)
                }
            }

        }
    }


    companion object {
        const val FRAGMENT_TAG = "add movie fragment"

        fun newInstance(): AddMovieFragment {
            return AddMovieFragment()
        }
    }


}