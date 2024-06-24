package com.example.newstart.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.newstart.FAVOURITES_KEY
import com.example.newstart.SHARED_PREFERENCES_FILE_NAME
import com.example.newstart.data.ResponseResult
import javax.inject.Inject

/**
 * Created by Roy
 */
class LocalData @Inject constructor(val context: Context) {

    fun getCachedFavourites(): ResponseResult<Set<String>> {
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, 0)
        return ResponseResult.Success(sharedPref.getStringSet(FAVOURITES_KEY, setOf()) ?: setOf())
    }

    fun isFavourite(id: String): ResponseResult<Boolean> {
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, 0)
        val cache = sharedPref.getStringSet(FAVOURITES_KEY, setOf<String>()) ?: setOf()
        return ResponseResult.Success(cache.contains(id))
    }

    fun cacheFavourites(ids: Set<String>): ResponseResult<Boolean> {
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, 0)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putStringSet(FAVOURITES_KEY, ids)
        editor.apply()
        val isSuccess = editor.commit()
        return ResponseResult.Success(isSuccess)
    }

    fun removeFromFavourites(id: String): ResponseResult<Boolean> {
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, 0)
        var set = sharedPref.getStringSet(FAVOURITES_KEY, mutableSetOf<String>())?.toMutableSet()
            ?: mutableSetOf()
        if (set.contains(id)) {
            set.remove(id)
        }
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.apply()
        editor.commit()
        editor.putStringSet(FAVOURITES_KEY, set)
        editor.apply()
        val isSuccess = editor.commit()
        return ResponseResult.Success(isSuccess)
    }
}

