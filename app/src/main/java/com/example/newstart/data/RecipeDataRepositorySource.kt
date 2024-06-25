package com.example.newstart.data

import com.example.newstart.data.dto.RecipesItem
import com.example.newstart.domain.DataError
import com.example.newstart.domain.ResponseResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by Roy
 */
interface RecipeDataRepositorySource {


    suspend fun requestRecipes(): Flow<ResponseResult<List<RecipesItem>,DataError.Network>>
    suspend fun addToFavourite(id: String): Flow<ResponseResult<Boolean, DataError.Local>>
    suspend fun removeFromFavourite(id: String): Flow<ResponseResult<Boolean, DataError.Local>>
    suspend fun isFavourite(id: String): Flow<ResponseResult<Boolean, DataError.Local>>

}