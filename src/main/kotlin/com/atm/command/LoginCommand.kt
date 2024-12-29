package org.example.com.atm.command

class LoginCommand : ICommand {
    override fun execute(vararg numbers: String) {
        println("Login command executed")
    }
}