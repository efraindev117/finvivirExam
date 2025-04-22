package com.xsoftcdmx.finvivirexam.core.common
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

abstract class BaseRepository {
     fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> Response<T>
    ): Flow<Resource<T>> = flow {
        emit(Resource.Loading)
        val response = apiCall()
        if (response.isSuccessful) {
            val data = response.body()
            if (data != null) {
                emit(Resource.Success(data))
            } else {
                val error = response.errorBody()
                if (error != null) {
                    emit(Resource.Failure(IOException(error.toString())))
                } else {
                    emit(Resource.Failure(IOException("something went wrong")))
                }
            }
        } else {
            emit(Resource.Failure(Throwable(response.errorBody().toString())))
        }
    }.catch { e ->
        e.printStackTrace()
        emit(Resource.Failure(Exception(e)))
    }.flowOn(dispatcher)
}