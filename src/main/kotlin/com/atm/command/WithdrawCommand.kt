package org.example.com.atm.command

class WithdrawCommand : ICommand {
    override fun execute(vararg numbers: String) {
        println("Withdraw command executed")
    }
}