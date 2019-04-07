package dev.bibuti.greenlight.models

import com.google.gson.annotations.SerializedName

data class Company(
        @SerializedName("bs")
        val bs: String,
        @SerializedName("catchPhrase")
        val catchPhrase: String,
        @SerializedName("name")
        val companyName: String
)