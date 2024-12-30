package org.example.com.atm.command

import org.example.com.atm.core.AccountService

class TransferCommand : ICommand {
    override fun execute(vararg input: String) {
        if (AccountService.loggedInUser == null) {
            throw Exception("No user logged in")
        }
        if (input.count() != 2) {
            throw Exception("Invalid number of arguments")
        }
        val toUser = AccountService.getUser(input[0]) ?: throw Exception("User ${input[0]} not found")
        val amount = input[1].toInt()
        AccountService.loggedInUser!!.transfer(toUser, amount);
        AccountService.loggedInUser!!.printStats()
    }
}