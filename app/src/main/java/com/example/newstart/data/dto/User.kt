package com.example.newstart.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Roy
 */
@Parcelize
data class User(
    @SerializedName("email")
    val email: String = "",
    @SerializedName("latlng")
    val latlng: String = "",
    @SerializedName("name")
    val name: String = ""
) : Parcelable