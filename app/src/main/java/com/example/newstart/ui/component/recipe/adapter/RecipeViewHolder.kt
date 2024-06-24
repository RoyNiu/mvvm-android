package com.task.ui.component.recipes.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.newstart.R
import com.example.newstart.data.dto.RecipesItem
import com.example.newstart.databinding.RecipeItemBinding
import com.example.newstart.ui.base.RecyclerItemListener
import com.example.newstart.utils.loadImage

/**
 * Created by Roy
 */

class RecipeViewHolder(private val itemBinding: RecipeItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(recipesItem: RecipesItem, recyclerItemListener: RecyclerItemListener<RecipesItem>) {
        itemBinding.tvCaption.text = recipesItem.description
        itemBinding.tvName.text = recipesItem.name
        itemBinding.ivRecipeItemImage.loadImage(recipesItem.thumb,R.drawable.ic_healthy_food,R.drawable.ic_healthy_food)
        itemBinding.rlRecipeItem.setOnClickListener { recyclerItemListener.onItemSelected(recipesItem) }
    }
}

