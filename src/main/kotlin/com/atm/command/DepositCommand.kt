package org.example.com.atm.command

class DepositCommand : ICommand {
    override fun execute(vararg numbers: String) {
        println("Deposit command executed")
    }
}