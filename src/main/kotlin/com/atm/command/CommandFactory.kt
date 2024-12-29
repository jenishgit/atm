package org.example.com.atm.command

class CommandFactory {

    companion object {
        val CommandInstances = mapOf(
            "login" to LoginCommand(),
            "deposit" to DepositCommand(),
            "withdraw" to WithdrawCommand(),
            "transfer" to TransferCommand(),
            "logout" to LogoutCommand()
        )
    }

    fun getCommand(commandText: String): ICommand {
        val commandEnum = getCommandEnum(commandText) ?: throw Exception("Invalid command")
        return CommandInstances[commandEnum.command]!!
    }

    private fun getCommandEnum(command: String): Commands? {
        return Commands.entries.firstOrNull { it.command.equals(command, ignoreCase = true) }
    }
}