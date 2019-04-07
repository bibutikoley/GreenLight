package dev.bibuti.greenlight.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.bibuti.greenlight.models.Users

@Dao
interface UserDao {

    @Query("select * from Users")
    fun daoGetAllUsers(): LiveData<List<Users>>

    @Delete
    suspend fun daoDeleteUser(users: Users)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun daoInsertUsers(users: List<Users>)

}