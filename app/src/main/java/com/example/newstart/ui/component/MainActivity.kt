package com.example.newstart.ui.component

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newstart.data.ResponseResult
import com.example.newstart.data.dto.RecipesItem
import com.example.newstart.fortest.printD
import com.example.newstart.ui.base.BaseActivity
import com.example.newstart.ui.component.recipe.RecipeListViewModel
import com.example.newstart.ui.theme.NewStartTheme
import com.example.newstart.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val recipesListViewModel: RecipeListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    }


    override fun initViewModel() {
//        setContent {
//            NewStartTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
        recipesListViewModel.getRecipes()
        observe(recipesListViewModel.recipesLiveData, ::handleRecipesList)
    }

    private fun handleRecipesList(status: ResponseResult<List<RecipesItem>>) {
        when (status) {
            is ResponseResult.Loading -> printD("loading")
            is ResponseResult.Success -> status.data?.let { printD(it.toString()) }
            is ResponseResult.Error -> {
//                showDataView(false)
                status.errorCode?.let { recipesListViewModel.showToastMessage(it) }
            }
        }
    }

    override fun initView() {
        setContent {
            NewStartTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(name = "Android", modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(

        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewStartTheme {
        Greeting("Android")
    }
}