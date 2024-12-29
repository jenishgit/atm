package org.example.com.atm.command

interface ICommand {
    fun execute(vararg numbers: String)
}