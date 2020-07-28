package ru.skillbranch.kotlinexample

import androidx.annotation.VisibleForTesting
import kotlin.math.log

object UserHolder {
    private val map = mutableMapOf<String, User>()

    fun registerUser(
        fullName:String,
        email:String,
        password:String
    ):User{

           return User.makeUser(fullName,email=email, password = password)
                .also {user ->
                    if (map.containsKey(user.login))
                        throw IllegalArgumentException("A user with this email already exists")
                    else
                    map[user.login] = user}

            
    }

    fun registerUserByPhone(fullName: String, rawPhone:String):User{

        return if ((rawPhone.replace("\\W".toRegex(),"").replace("\\d".toRegex(),"").isEmpty()) &&
            (rawPhone.replace("[^+\\d]".toRegex(),"").length == 12))


            User.makeUser(fullName,phone = rawPhone)
                .also {user ->
                    if (map.containsKey(user.login))
                        throw IllegalArgumentException("A user with this phone already exists")
                    else
                        map[user.login] = user }
        else
            throw IllegalArgumentException("Enter a valid phone number starting with a + and containing 11 digits")
    }

    fun loginUser(login: String, password: String): String? {
        val user = map[login.trim()] ?:  map[login.replace("[^+\\d]".toRegex(),"")]
        return user?.run {
            if (checkPassword(password)) this.userInfo
            else null
        }
    }

    fun requestAccessCode(login:String):Unit{
        val user = map[login.trim()] ?: map[login.replace("[^+\\d]".toRegex(),"")]
        user?.changeAccessCode()
    }

    fun importUsers(list: List<String>) :List<User> {
        return list.map {
            User.parseCSV(it).also { user ->
                map[user.login] = user
            }
        }

    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun clearHolder(){
        map.clear()
    }

}