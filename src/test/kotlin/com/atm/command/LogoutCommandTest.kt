package com.atm.command

import io.mockk.*
import org.example.com.atm.command.LogoutCommand
import org.example.com.atm.core.AccountService
import org.example.com.atm.core.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class LogoutCommandTest {
    @BeforeEach
    fun setup() {
        // Clear any static state before each test
        clearAllMocks()
    }

    @Test
    fun `execute should throw exception if arguments are passed`() {
        // Arrange
        val command = LogoutCommand()

        // Act & Assert
        assertFailsWith<Exception> {
            command.execute("extraArgument")
        }
    }

    @Test
    fun `execute should throw exception if no user is logged in`() {
        // Arrange
        mockkObject(AccountService.Companion) // Mock the companion object
        AccountService.loggedInUser = null // Simulate no user logged in

        val command = LogoutCommand()

        // Act & Assert
        assertFailsWith<Exception> {
            command.execute()
        }

        // Verify that no interaction occurred with the user
        verify(exactly = 0) { AccountService.logout() }
    }

    @Test
    fun `execute should call goodBye and logout when a user is logged in`() {
        // Arrange
        mockkObject(AccountService.Companion) // Mock the companion object
        val mockUser = mockk<User>(relaxed = true) // Mock the User class
        AccountService.loggedInUser = mockUser // Simulate a logged-in user

        // Mock the logout function to do nothing
        every { AccountService.logout() } just Runs

        val command = LogoutCommand()

        // Act
        command.execute()

        // Assert
        // Verify that goodBye and logout methods are called
        verify { mockUser.goodBye() }
        verify { AccountService.logout() }

        // Clean up
        unmockkObject(AccountService.Companion)
    }
}