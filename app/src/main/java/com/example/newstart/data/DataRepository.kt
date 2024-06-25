package com.example.newstart.data

import com.example.newstart.data.dto.RecipesItem
import com.example.newstart.data.local.LocalData
import com.example.newstart.data.remote.EasyApi
import com.example.newstart.data.remote.feature.RecipeRemote
import com.example.newstart.data.remote.service.RecipesService
import com.example.newstart.domain.DataError
import com.example.newstart.domain.ResponseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by Roy
 */
class DataRepository @Inject constructor(
    private val remoteRepository: RecipeRemote,
    private val localRepository: LocalData,
    private val ioDispatcher: CoroutineContext
) : RecipeDataRepositorySource {

    override suspend fun requestRecipes(): Flow<ResponseResult<List<RecipesItem>, DataError.Network>> {
        return flow {
            val result = EasyApi.apiCall(RecipesService::class.java) {
                fetchRecipes()
            }

            emit(result)
        }.flowOn(ioDispatcher)
    }


    override suspend fun addToFavourite(id: String): Flow<ResponseResult<Boolean, DataError.Local>> {
        return flow {
           when(val result = localRepository.getCachedFavourites()){
               is ResponseResult.Error -> {
                   emit(ResponseResult.Error(DataError.Local.UNKNOWN))
               }
               is ResponseResult.Success -> {
                    result.data.toMutableSet().let { set->
                        val isAdded = set.add(id)
                        if (isAdded){
                            emit(localRepository.cacheFavourites(set))
                        }else{
                            emit(ResponseResult.Success(false))
                        }
                    }
               }

               is ResponseResult.Loading -> TODO()
           }
        }.flowOn(ioDispatcher)
    }

    override suspend fun removeFromFavourite(id: String): Flow<ResponseResult<Boolean, DataError.Local>> {
        return flow {
            emit(localRepository.removeFromFavourites(id))
        }.flowOn(ioDispatcher)
    }

    override suspend fun isFavourite(id: String): Flow<ResponseResult<Boolean, DataError.Local>> {
        return flow {
            emit(localRepository.isFavourite(id))
        }.flowOn(ioDispatcher)
    }
}