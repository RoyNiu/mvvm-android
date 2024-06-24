package com.task.ui.component.recipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newstart.data.dto.RecipesItem
import com.example.newstart.databinding.RecipeItemBinding
import com.example.newstart.ui.base.RecyclerItemListener
import com.example.newstart.ui.component.recipe.RecipeListViewModel


/**
 * Created by Roy
 */

class RecipesAdapter(
    private val recipesListViewModel: RecipeListViewModel,
    private val recipes: List<RecipesItem>
) : RecyclerView.Adapter<RecipeViewHolder>() {

    private val onItemClickListener: RecyclerItemListener<RecipesItem> =
        object : RecyclerItemListener<RecipesItem> {
            override fun onItemSelected(recipe: RecipesItem) {
                recipesListViewModel.openRecipeDetails(recipe)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemBinding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}

