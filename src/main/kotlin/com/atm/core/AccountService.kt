package org.example.com.atm.core

class AccountService {
    companion object {
        var loggedInUser: User? = null;
        private val users = mutableMapOf<String, User>()

        fun createUserIfNotExisting(name: String) : User {
            val user = getUser(name);
            if (user == null) {
                val newUser = User(name, 0)
                users[name] = newUser
                return newUser
            }
            return user
        }

        fun login(user: User) {
            loggedInUser = user
        }

        fun logout() {
            loggedInUser = null
        }

        private fun getUser(name: String): User? {
            if (users.containsKey(name)) {
                return users[name]
            }
            return null;
        }
    }

}