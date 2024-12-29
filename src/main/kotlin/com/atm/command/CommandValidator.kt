package org.example.com.atm.command

class CommandValidator {
    fun validate(command: String): Boolean {
        return Commands.entries.any { it.command.equals(command, ignoreCase = true) }
    }
}