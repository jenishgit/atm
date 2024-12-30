package org.example

import org.example.com.atm.command.CommandFactory

fun main() {
    val commandFactory = CommandFactory()
    while (true) {
        print("Enter command: ")
        val input = readln().split(" ")
        val command = input[0]
        val arguments = input.drop(1).toTypedArray()
        try {
            commandFactory.getCommand(command).execute(*arguments)
        } catch (e: Exception) {
            println(e.message)
        }
    }
}