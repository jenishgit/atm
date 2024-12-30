package org.example.com.atm.command

import org.example.com.atm.core.AccountService

class LoginCommand : ICommand {
    override fun execute(vararg input: String) {
        if (input.count() != 1) {
            throw Exception("Invalid number of arguments")
        }
        val name = input[0];
        val user = AccountService.createUserIfNotExisting(name)
        AccountService.login(user)
        user.greet()
        user.printStats()
    }
}