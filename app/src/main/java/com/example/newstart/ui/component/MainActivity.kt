package com.example.newstart.ui.component

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newstart.R
import com.example.newstart.RECIPE_ITEM_KEY
import com.example.newstart.domain.ResponseResult
import com.example.newstart.data.dto.RecipesItem
import com.example.newstart.databinding.HomeActivityBinding
import com.example.newstart.domain.DataError
import com.example.newstart.ui.base.BaseActivity
import com.example.newstart.ui.component.detial.DetailActivity
import com.example.newstart.ui.component.recipe.RecipeListViewModel
import com.example.newstart.utils.SingleEvent
import com.example.newstart.utils.observe
import com.example.newstart.utils.observeEvent
import com.example.newstart.utils.setupSnackbar
import com.example.newstart.utils.showToast
import com.example.newstart.utils.toGone
import com.example.newstart.utils.toVisible
import com.google.android.material.snackbar.Snackbar
import com.task.ui.component.recipes.adapter.RecipesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: HomeActivityBinding

    private val recipesListViewModel: RecipeListViewModel by viewModels()
    private lateinit var recipesAdapter: RecipesAdapter
    override fun initData() {
        observe(recipesListViewModel.recipesLiveData, ::handleRecipesList)
        observe(recipesListViewModel.recipeSearchFound, ::showSearchResult)
        observeEvent(recipesListViewModel.openRecipeDetails, ::navigateToDetailsScreen)
        observeSnackBarMessages(recipesListViewModel.showSnackBar)
        observeToast(recipesListViewModel.showToast)
    }

    override fun initView() {
        binding = HomeActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.recipe)
        val layoutManager = LinearLayoutManager(this)
        binding.rvRecipesList.layoutManager = layoutManager
        binding.rvRecipesList.setHasFixedSize(true)
        recipesListViewModel.getRecipes()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_actions, menu)
        // Associate searchable configuration with the SearchView
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_by_name)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                handleSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                recipesListViewModel.onSearchClick(newText)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> recipesListViewModel.getRecipes()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleSearch(query: String) {
        if (query.isNotEmpty()) {
            showLoadingView()
            recipesListViewModel.onSearchClick(query)
        }
    }


    private fun bindListData(recipes: List<RecipesItem>) {
        if (recipes.isNotEmpty()) {
            recipesAdapter = RecipesAdapter(recipesListViewModel, recipes)
            binding.rvRecipesList.adapter = recipesAdapter
            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun navigateToDetailsScreen(navigateEvent: SingleEvent<RecipesItem>) {
        navigateEvent.getContentIfNotHandled()?.let {
            val nextScreenIntent = Intent(this, DetailActivity::class.java).apply {
                putExtra(RECIPE_ITEM_KEY, it)
            }
            startActivity(nextScreenIntent)
        }
    }


    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }


    private fun showDataView(show: Boolean) {
        binding.tvNoData.visibility = if (show) View.GONE else View.VISIBLE
        binding.rvRecipesList.visibility = if (show) View.VISIBLE else View.GONE
        binding.pbLoading.toGone()
    }

    private fun showLoadingView() {
        binding.pbLoading.toVisible()
        binding.tvNoData.toGone()
        binding.rvRecipesList.toGone()
    }


    private fun showSearchResult(recipesItem: RecipesItem) {
        recipesListViewModel.openRecipeDetails(recipesItem)
        binding.pbLoading.toGone()
    }

    private fun handleRecipesList(status: ResponseResult<List<RecipesItem>, DataError>) {
        when (status) {
            is ResponseResult.Loading -> showLoadingView()
            is ResponseResult.Success -> bindListData(recipes = status.data)
            is ResponseResult.Error -> {
                showDataView(false)
                recipesListViewModel.handleError(status.error)
            }
        }
    }



}





