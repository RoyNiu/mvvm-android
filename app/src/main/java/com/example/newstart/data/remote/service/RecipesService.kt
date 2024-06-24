package com.example.newstart.data.remote.service

import com.example.newstart.data.dto.RecipesItem
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Roy
 */
interface RecipesService {
    @GET("recipes.json")
    suspend fun fetchRecipes(): Response<List<RecipesItem>>
}
