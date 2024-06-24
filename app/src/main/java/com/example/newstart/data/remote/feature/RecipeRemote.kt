package com.example.newstart.data.remote.feature

import com.example.newstart.data.remote.ApiServiceGenerator
import com.example.newstart.data.remote.RemoteDataBase
import com.example.newstart.data.remote.service.RecipesService
import javax.inject.Inject

/**
 * Created by Roy
 */

class RecipeRemote @Inject constructor(serviceGenerator: ApiServiceGenerator) :
    RemoteDataBase<RecipesService>(serviceGenerator) {

    override fun getService(): Class<RecipesService> {
        return RecipesService::class.java
    }


}
