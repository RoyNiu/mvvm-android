package com.example.newstart.ui.component.recipe

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newstart.R
import com.example.newstart.data.RecipeDataRepositorySource
import com.example.newstart.data.dto.RecipesItem
import com.example.newstart.domain.DataError
import com.example.newstart.domain.Error
import com.example.newstart.domain.ResponseResult
import com.example.newstart.ui.base.BaseViewModel
import com.example.newstart.utils.AppContext
import com.example.newstart.utils.SingleEvent
import com.example.newstart.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

/**
 * Created by Roy
 */
@HiltViewModel
class RecipeListViewModel @Inject
constructor(private val dataRepositoryRepository: RecipeDataRepositorySource) : BaseViewModel() {

    /**
     * Data --> LiveData, Exposed as LiveData, Locally in viewModel as MutableLiveData
     */
    private var originData: List<RecipesItem>? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val recipesLiveDataPrivate = MutableLiveData<ResponseResult<List<RecipesItem>, DataError>>()
    val recipesLiveData: LiveData<ResponseResult<List<RecipesItem>, DataError>> get() = recipesLiveDataPrivate


    //TODO check to make them as one Resource
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val recipeSearchFoundPrivate: MutableLiveData<RecipesItem> = MutableLiveData()
    val recipeSearchFound: LiveData<RecipesItem> get() = recipeSearchFoundPrivate

//    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
//    val noSearchFoundPrivate: MutableLiveData<Unit> = MutableLiveData()
//    val noSearchFound: LiveData<Unit> get() = noSearchFoundPrivate

    /**
     * UI actions as event, user action is single one time event, Shouldn't be multiple time consumption
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val openRecipeDetailsPrivate = MutableLiveData<SingleEvent<RecipesItem>>()
    val openRecipeDetails: LiveData<SingleEvent<RecipesItem>> get() = openRecipeDetailsPrivate

    /**
     * Error handling as UI
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showSnackBarPrivate = MutableLiveData<SingleEvent<Any>>()
    val showSnackBar: LiveData<SingleEvent<Any>> get() = showSnackBarPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate


    fun getRecipes() {
        viewModelScope.launch {
            recipesLiveDataPrivate.value = ResponseResult.Loading()
            wrapEspressoIdlingResource {
                dataRepositoryRepository.requestRecipes().collect {
                    recipesLiveDataPrivate.value = it
                    if (it is ResponseResult.Success) originData = it.data
                }
            }
        }
    }

    fun openRecipeDetails(recipe: RecipesItem) {
        openRecipeDetailsPrivate.value = SingleEvent(recipe)
    }

    fun onSearchClick(recipeName: String) {
        val filterList = originData?.filter {
            it.name.lowercase(Locale.ROOT).contains(recipeName.lowercase(Locale.ROOT))
        }?: emptyList()

        if (filterList.isEmpty()) showToastPrivate.value = SingleEvent(R.string.search_no_result)
        recipesLiveDataPrivate.postValue(ResponseResult.Success(filterList))
    }

    enum class SearchError : Error {
        SEARCH_NO_RESULT
    }

    fun handleError(e: DataError) {
        showToastPrivate.value = SingleEvent(getErrorStr(e))
    }


    override fun getFeatureErrorMap(): Map<out Error, String> {
        val map: Map<out Error, String> =
            mapOf(SearchError.SEARCH_NO_RESULT to AppContext.getString(R.string.search_no_result))
        return map
    }


}