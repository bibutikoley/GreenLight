package dev.bibuti.greenlight.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Users(
        @SerializedName("address")
        @Embedded
        val address: Address,
        @SerializedName("company")
        @Embedded
        val company: Company,
        @SerializedName("email")
        val email: String,
        @SerializedName("id")
        @PrimaryKey
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("username")
        val username: String,
        @SerializedName("website")
        val website: String
)