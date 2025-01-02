package com.atm.command

import org.example.com.atm.command.WithdrawCommand
import org.example.com.atm.core.AccountService
import org.example.com.atm.core.User
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WithdrawCommandTest {
    private val withdrawCommand = WithdrawCommand()

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
            withdrawCommand.execute("500")
        }
        assertEquals("No user logged in", exception.message)
    }

    @Test
    fun `execute should throw exception when invalid number of arguments are passed`() {
        val exception = assertThrows(Exception::class.java) {
            withdrawCommand.execute()
        }
        assertEquals("Invalid number of arguments", exception.message)
    }

    @Test
    fun `execute should throw exception when withdrawal amount exceeds balance`() {
        val exception = assertThrows(Exception::class.java) {
            withdrawCommand.execute("1400")
        }
        assertEquals("Insufficient funds", exception.message)
    }

    @Test
    fun `execute should withdraw the amount when valid input is provided`() {
        withdrawCommand.execute("400")

        assertEquals(600, AccountService.loggedInUser!!.balance)
    }

    @Test
    fun `execute should throw exception when the input amount is not an integer`() {
        assertThrows(NumberFormatException::class.java) {
            withdrawCommand.execute("invalid_amount")
        }
    }


}