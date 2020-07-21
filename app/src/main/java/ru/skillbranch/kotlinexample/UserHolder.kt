package ru.skillbranch.kotlinexample

import androidx.annotation.VisibleForTesting

object UserHolder {
    private val map = mutableMapOf<String, User>()

    fun registerUser(
        fullName:String,
        email:String,
        password:String
    ):User{
        return if (map[email.trimIndent().toLowerCase()] ==null)
            User.makeUser(fullName,email=email, password = password)
                .also {user -> map[user.login] = user}
        else
            throw IllegalArgumentException("A user with this email already exists")
    }

    fun registerUserByPhone(fullName: String, rawPhone:String):User{
        //TODO
    }

    fun loginUser(login: String, password: String): String? {
        return map[login.trim()]?.run {
            if (checkPassword(password)) this.userInfo
            else null
        }
    }

    fun requestAccessCode(login:String):Unit{
        //TODO
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun clearHolder(){
        map.clear()
    }

}