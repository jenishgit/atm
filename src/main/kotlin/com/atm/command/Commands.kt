package org.example.com.atm.command

enum class Commands(val command: String) {
    LOGIN("login"),
    DEPOSIT("deposit"),
    WITHDRAW("withdraw"),
    TRANSFER("transfer"),
    LOGOUT("logout");

    override fun toString(): String {
        return command
    }
}