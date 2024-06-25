package com.example.newstart.ui.component.detial

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.newstart.R
import com.example.newstart.RECIPE_ITEM_KEY
import com.example.newstart.domain.ResponseResult
import com.example.newstart.data.dto.RecipesItem
import com.example.newstart.databinding.DetailsLayoutBinding
import com.example.newstart.domain.DataError
import com.example.newstart.ui.base.BaseActivity
import com.example.newstart.utils.loadImage
import com.example.newstart.utils.observe
import com.example.newstart.utils.toGone
import com.example.newstart.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Roy
 */
@AndroidEntryPoint
class DetailActivity : BaseActivity() {

    private val viewModel: DetailsViewModel by viewModels()

    private lateinit var binding: DetailsLayoutBinding
    private var menu: Menu? = null


    override fun initView() {
        binding = DetailsLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initIntentData(intent.getParcelableExtra(RECIPE_ITEM_KEY) ?: RecipesItem())
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        this.menu = menu
        viewModel.isFavourites()
        return true
    }

    fun onClickFavorite(mi: MenuItem) {
        mi.isCheckable = false
        val currentState = viewModel.isFavourite.value
        if (currentState is ResponseResult.Success && currentState.data) {
            viewModel.removeFromFavourites()
        } else {
            viewModel.addToFavourites()
        }
    }

    override fun initData() {
        observe(viewModel.recipeData, ::initializeView)
        observe(viewModel.isFavourite, ::handleIsFavourite)
    }

    private fun handleIsFavourite(isFavourite: ResponseResult<Boolean, DataError.Local>) {
        when (isFavourite) {
            is ResponseResult.Loading -> {
                binding.pbLoading.toVisible()
            }

            is ResponseResult.Success -> {
                isFavourite.data.let {
                    handleIsFavouriteUI(it)
                    menu?.findItem(R.id.add_to_favorite)?.isCheckable = true
                    binding.pbLoading.toGone()
                }
            }

            is ResponseResult.Error -> {
                menu?.findItem(R.id.add_to_favorite)?.isCheckable = true
                binding.pbLoading.toGone()
            }
        }
    }

    private fun handleIsFavouriteUI(isFavourite: Boolean) {
        menu?.let {
            it.findItem(R.id.add_to_favorite)?.icon =
                if (isFavourite) {
                    ContextCompat.getDrawable(this, R.drawable.ic_star_24)
                } else {
                    ContextCompat.getDrawable(this, R.drawable.ic_outline_star_border_24)
                }
        }
    }

    private fun initializeView(recipesItem: RecipesItem) {
        binding.tvName.text = recipesItem.name
        binding.tvHeadline.text = recipesItem.headline
        binding.tvDescription.text = recipesItem.description
        binding.ivRecipeImage.loadImage(
            recipesItem.image,
            R.drawable.ic_healthy_food_small,
            R.drawable.ic_healthy_food_small
        )
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }


}
