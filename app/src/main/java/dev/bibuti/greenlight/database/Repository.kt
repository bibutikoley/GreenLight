package dev.bibuti.greenlight.database

import android.app.Application

import androidx.lifecycle.LiveData
import dev.bibuti.greenlight.database.daos.UserDao
import dev.bibuti.greenlight.models.Users
import dev.bibuti.greenlight.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class Repository(application: Application) {

    private val userDao: UserDao
    private val usersLiveData: LiveData<List<Users>>

    init {
        val greenLightDB = GreenLightDB.getInstance(application)
        userDao = greenLightDB!!.userDao()
        usersLiveData = userDao.daoGetAllUsers()
    }

    fun repoUsersList(): LiveData<List<Users>> {
        return usersLiveData
    }

    fun repoInsertUsers(usersList: List<Users>) {
        runBlocking(Dispatchers.Default) {
            userDao.daoInsertUsers(usersList)
        }
    }

    fun repoDeleteUser(user: Users) {
        runBlocking(Dispatchers.Default) {
            userDao.daoDeleteUser(user)
        }
    }

    fun repoMakeNetworkCall() {
        runBlocking(Dispatchers.Default) {
            val usersList = RetrofitClient.endpoints.users.execute().body()
            if (usersList != null) {
                userDao.daoInsertUsers(usersList)
            }
        }
    }
}
