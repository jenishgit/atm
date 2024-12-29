package org.example.com.atm.command

import org.example.com.atm.core.AccountService

class LogoutCommand : ICommand {
    override fun execute(vararg input: String) {
        if (input.isNotEmpty()) {
            throw Exception("Invalid number of arguments")
        }
        if (AccountService.loggedInUser == null) {
            throw Exception("No user logged in")
        }
        AccountService.loggedInUser!!.goodBye()
        AccountService.logout()
    }
}