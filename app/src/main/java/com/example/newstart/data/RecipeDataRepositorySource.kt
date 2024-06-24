package com.example.newstart.data

import com.example.newstart.data.dto.RecipesItem
import kotlinx.coroutines.flow.Flow

/**
 * Created by Roy
 */
interface RecipeDataRepositorySource {

    suspend fun requestRecipes(): Flow<ResponseResult<List<RecipesItem>>>
    suspend fun addToFavourite(id: String): Flow<ResponseResult<Boolean>>
    suspend fun removeFromFavourite(id: String): Flow<ResponseResult<Boolean>>
    suspend fun isFavourite(id: String): Flow<ResponseResult<Boolean>>

}