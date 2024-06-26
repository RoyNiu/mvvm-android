package com.example.newstart.ui.component.detial

import android.provider.ContactsContract.Data
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newstart.data.RecipeDataRepositorySource
import com.example.newstart.domain.ResponseResult
import com.example.newstart.data.dto.RecipesItem
import com.example.newstart.domain.DataError
import com.example.newstart.ui.base.BaseViewModel
import com.example.newstart.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Roy
 */
@HiltViewModel
open class DetailsViewModel @Inject constructor(private val dataRepository: RecipeDataRepositorySource) :
    BaseViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val recipePrivate = MutableLiveData<RecipesItem>()
    val recipeData: LiveData<RecipesItem> get() = recipePrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val isFavouritePrivate = MutableLiveData<ResponseResult<Boolean, DataError>>()
    val isFavourite: LiveData<ResponseResult<Boolean, DataError>> get() = isFavouritePrivate

    fun initIntentData(recipe: RecipesItem) {
        recipePrivate.value = recipe
    }

    open fun addToFavourites() {
        viewModelScope.launch {
            isFavouritePrivate.value = ResponseResult.Loading()
            wrapEspressoIdlingResource {
                recipePrivate.value?.id?.let {
                    dataRepository.addToFavourite(it).collect { isAdded ->
                        isFavouritePrivate.value = isAdded
                    }
                }
            }
        }
    }

    fun removeFromFavourites() {
        viewModelScope.launch {
            isFavouritePrivate.value = ResponseResult.Loading()
            wrapEspressoIdlingResource {
                recipePrivate.value?.id?.let {
                    dataRepository.removeFromFavourite(it).collect { isRemoved ->
                        when (isRemoved) {
                            is ResponseResult.Success -> {
                                isRemoved.data.let {
                                    isFavouritePrivate.value =
                                        ResponseResult.Success(!isRemoved.data)
                                }
                            }

                            is ResponseResult.Error -> {
                                isFavouritePrivate.value = isRemoved
                            }

                            is ResponseResult.Loading -> {
                                isFavouritePrivate.value = isRemoved
                            }
                        }
                    }
                }
            }
        }
    }

    fun isFavourites() {
        viewModelScope.launch {
            isFavouritePrivate.value = ResponseResult.Loading()
            wrapEspressoIdlingResource {
                recipePrivate.value?.id?.let {
                    dataRepository.isFavourite(it).collect { isFavourites ->
                        isFavouritePrivate.value = isFavourites
                    }
                }
            }
        }
    }


}