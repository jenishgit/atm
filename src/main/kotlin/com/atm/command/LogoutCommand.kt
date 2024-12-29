package org.example.com.atm.command

class LogoutCommand : ICommand {
    override fun execute(vararg numbers: String) {
        println("Logout command executed")
    }
}