package org.example.com.atm.core

private const val AMOUNT_MUST_BE_POSITIVE = "Amount must be positive"

data class User(
    val name: String,
    var balance: Int,
    val owedFrom: MutableList<Pair<User, Int>> = mutableListOf(),
    val owedTo: MutableList<Pair<User, Int>> = mutableListOf()
) {

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

    fun transfer(to: User, value: Int) {
        val from = this
        var amount = value
        if (amount < 0) {
            throw Exception(AMOUNT_MUST_BE_POSITIVE)
        }
        from.owedTo.firstOrNull { it.first == to }?.let { pair ->
            if (pair.second > amount) {
                from.owedTo[from.owedTo.indexOfFirst { it.first == to }] = Pair(to, pair.second - amount)
                to.owedFrom[to.owedFrom.indexOfFirst { it.first == from }] = Pair(from, pair.second - amount)
                to.deposit(amount)
                from.withdraw(amount)
                return
            } else {
                from.owedTo.remove(pair)
                to.owedFrom.remove(Pair(from, pair.second))
                to.deposit(pair.second)
                from.withdraw(pair.second)
                amount -= pair.second
                if (amount == 0) return
            }
        }
        if (from.balance >= amount) {
            from.withdraw(amount)
            to.deposit(amount)
        } else {
            val loanAmount = amount - from.balance
            to.deposit(from.balance)
            from.withdraw(from.balance)
            to.owedFrom.add(Pair(from, loanAmount))
            from.owedTo.add(Pair(to, loanAmount))
        }
    }

    fun withdraw(value: Int) {
        if (value < 0) {
            throw Exception(AMOUNT_MUST_BE_POSITIVE)
        }
        if (balance < value) {
            throw Exception("Insufficient funds")
        }
        balance -= value
    }

    fun deposit(value: Int) {
        if (value < 0) {
            throw Exception(AMOUNT_MUST_BE_POSITIVE)
        }
        var amount = value;
        balance += amount
        if (owedTo.isNotEmpty()) {
            owedTo.stream().toList().forEach { (user, owedAmount) ->
                if (amount == 0) return
                if (owedAmount <= amount) {
                    transfer(user, owedAmount)
                    amount -= owedAmount
                } else {
                    transfer(user, amount)
                    amount = 0
                }
            }
        }

    }

    override fun toString(): String {
        return name.toString()
    }
}
