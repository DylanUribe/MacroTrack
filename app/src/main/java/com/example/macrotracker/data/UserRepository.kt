package com.example.macrotracker.data

import com.example.macrotracker.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User) = userDao.insert(user)

    suspend fun loginUser(email: String, password: String) =
        userDao.login(email, password)

    suspend fun updateUser(user: User) = userDao.update(user)

    suspend fun getUserById(userId: Int) = userDao.getUserById(userId)
}
