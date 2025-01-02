package com.atm.command

import org.example.com.atm.command.TransferCommand
import org.example.com.atm.core.AccountService
import org.example.com.atm.core.User
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TransferCommandTest {
    private val transferCommand = TransferCommand()

    @BeforeEach
    fun setup() {
        // Reset the AccountService before each test
        AccountService.createUserIfNotExisting("Alice")
        AccountService.createUserIfNotExisting("Bob")
        AccountService.loggedInUser = AccountService.getUser("Alice");
    }

    @AfterEach
    fun tearDown() {
        // Clean up any state after tests
        AccountService.loggedInUser = null
    }

    @Test
    fun `execute should throw exception when no user is logged in`() {
        AccountService.loggedInUser = null
        val exception = assertThrows(Exception::class.java) {
            transferCommand.execute("Alice", "1000")
        }
        assertEquals("No user logged in", exception.message)
    }

    @Test
    fun `execute should throw exception when invalid number of arguments are passed`() {
        val exception = assertThrows(Exception::class.java) {
            transferCommand.execute("toUser")
        }
        assertEquals("Invalid number of arguments", exception.message)
    }

    @Test
    fun `execute should throw exception when recipient user is not found`() {
        val exception = assertThrows(Exception::class.java) {
            transferCommand.execute("nonExistentUser", "500")
        }
        assertEquals("User nonExistentUser not found", exception.message)
    }

    @Test
    fun `execute should successfully transfer amount when valid inputs are provided`() {
        AccountService.getUser("Alice")!!.deposit(500)
        AccountService.getUser("Bob")!!.deposit(400)
        transferCommand.execute("Bob", "300")

        assertEquals(200, AccountService.getUser("Alice")!!.balance)
        assertEquals(700, AccountService.getUser("Bob")!!.balance)
    }

    @Test
    fun `execute should throw exception when the transfer amount is not an integer`() {
        assertThrows(NumberFormatException::class.java) {
            transferCommand.execute("Bob", "invalid_amount")
        }
    }

}