package dev.bibuti.greenlight.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import dev.bibuti.greenlight.models.Users

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: Repository = Repository(application)
    private val allUsers: LiveData<List<Users>>

    init {
        allUsers = mRepository.repoUsersList()
    }

    fun getAllUser(): LiveData<List<Users>> {
        return allUsers
    }

    fun insertUsers(usersList: List<Users>) {
        mRepository.repoInsertUsers(usersList)
    }

    fun deleteUser(user: Users) {
        mRepository.repoDeleteUser(user)
    }

    fun makeNetworkRequest() {
        mRepository.repoMakeNetworkCall()
    }
}
