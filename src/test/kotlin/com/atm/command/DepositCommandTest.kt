package com.atm.command

import org.example.com.atm.command.DepositCommand
import org.example.com.atm.core.AccountService
import org.example.com.atm.core.User
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DepositCommandTest {

    val depositCommand = DepositCommand()
    @BeforeEach
    fun setup() {
        // Reset the AccountService before each test
        AccountService.loggedInUser = User("Alice", 1000)
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
            depositCommand.execute("1000")
        }
        assertEquals("No user logged in", exception.message)
    }

    @Test
    fun `execute should throw exception when invalid number of arguments are passed`() {
        val exception = assertThrows(Exception::class.java) {
            depositCommand.execute()
        }
        assertEquals("Invalid number of arguments", exception.message)
    }

    @Test
    fun `execute should deposit the amount when valid input is provided`() {
        depositCommand.execute("1000")

        assertEquals(2000, AccountService.loggedInUser!!.balance)
    }

    @Test
    fun `execute should throw exception when the input amount is not an integer`() {
        assertThrows(NumberFormatException::class.java) {
            depositCommand.execute("invalid_amount")
        }
    }
}