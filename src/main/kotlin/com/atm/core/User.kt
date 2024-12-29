package org.example.com.atm.core

data class User(
    val name: String,
    val balance: Int,
    val owedFrom: List<Pair<User, Int>> = emptyList(),
    val owedTo: List<Pair<User, Int>> = emptyList()) {

    fun printStats() {
        println("Your balance is $$balance")
        owedTo.forEach { (user, amount) ->
            println("Owed $${amount} to ${user.name}")
        }
        owedFrom.forEach { (user, amount) ->
            println("Owed $${amount} from ${user.name}")
        }
    }

    fun greet() {
        println("Hello, $name!")
    }

    fun goodBye() {
        println("Goodbye, $name!")
    }
}
