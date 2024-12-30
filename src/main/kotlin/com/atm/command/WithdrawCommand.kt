package org.example.com.atm.command

import org.example.com.atm.core.AccountService

class WithdrawCommand : ICommand {
    override fun execute(vararg input: String) {
        if (AccountService.loggedInUser == null) {
            throw Exception("No user logged in")
        }
        if (input.count() != 1) {
            throw Exception("Invalid number of arguments")
        }
        val amount = input[0].toInt()
        AccountService.loggedInUser!!.withdraw(amount);
        AccountService.loggedInUser!!.printStats()
    }
}