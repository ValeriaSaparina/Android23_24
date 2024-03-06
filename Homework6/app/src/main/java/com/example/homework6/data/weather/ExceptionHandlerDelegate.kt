package com.example.homework6.data.weather

import com.example.homework6.R
import com.example.homework6.data.weather.exception.BadRequestException
import com.example.homework6.data.weather.exception.NotFoundException
import com.example.homework6.data.weather.exception.TooManyRequestsException
import com.example.homework6.data.weather.exception.UnexpectedError
import com.example.homework6.data.weather.exception.UserNotAuthorizedException
import com.example.homework6.utils.ResManager
import retrofit2.HttpException

class ExceptionHandlerDelegate(
    private val resManager: ResManager
) {

    fun handleException(ex: Throwable): Throwable {
        return when (ex) {
            is HttpException -> {
                when (ex.code()) {
                    400 -> BadRequestException(message = resManager.getString(R.string.bad_request_exception))
                    401 -> UserNotAuthorizedException(message = resManager.getString(R.string.user_not_authorized))
                    404 -> NotFoundException(message = resManager.getString(R.string.data_not_found))
                    429 -> TooManyRequestsException(message = resManager.getString(R.string.too_many_requests))
                    else -> UnexpectedError(message = resManager.getString(R.string.unexpected_error))
                }
            }
            else -> UnexpectedError(message = resManager.getString(R.string.unexpected_error))
        }
    }

}