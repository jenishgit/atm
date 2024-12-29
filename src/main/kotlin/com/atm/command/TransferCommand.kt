package org.example.com.atm.command

class TransferCommand : ICommand {
    override fun execute(vararg numbers: String) {
        println("Transfer command executed")
    }
}