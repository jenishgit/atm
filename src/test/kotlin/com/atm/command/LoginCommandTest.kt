package com.atm.command

import org.example.com.atm.command.LoginCommand
import org.example.com.atm.core.AccountService
import org.example.com.atm.core.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import kotlin.test.assertFailsWith

class LoginCommandTest {

    @AfterEach
    fun cleanUp() {
        unmockkAll()
    }

    @Test
    fun `execute should throw exception if the number of arguments is not 1`() {
        val loginCommand = LoginCommand()
        assertFailsWith<Exception> {
            loginCommand.execute()
        }
        assertFailsWith<Exception> {
            loginCommand.execute("John", "Doe")
        }
    }

    @Test
    fun `execute should create user and call greet and printStats methods for valid input`() {
        val mockUser = mockk<User>(relaxed = true)
        mockkObject(AccountService.Companion)

        every {
            AccountService.createUserIfNotExisting("John")
        } returns mockUser
        // Call the execute method
        val command = LoginCommand()
        command.execute("John")

        // Verify if methods greet() and printStats() were called
        verify{
            mockUser.greet()
            mockUser.printStats()
        }
    }
}