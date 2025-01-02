package com.atm.core

import org.example.com.atm.core.AccountService
import org.example.com.atm.core.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class UserTest {

    @BeforeEach
    fun setup() {
        // Reset the AccountService before each test
        AccountService.createUserIfNotExisting("Alice")
        AccountService.createUserIfNotExisting("Bob")
        AccountService.loggedInUser = AccountService.getUser("Alice")
    }

    @Test
    fun `User should be correctly initialized`() {
        val user = User("John", 1000)

        assertEquals("John", user.name)
        assertEquals(1000, user.balance)
        assertEquals(emptyList<Pair<User, Int>>(), user.owedFrom)
        assertEquals(emptyList<Pair<User, Int>>(), user.owedTo)
    }

    @Test
    fun `greet should print greeting message`() {
        // Create user
        val user = User("Jenish", 0)

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        user.greet()
        val output = outputStream.toString()

        // Assertions for correct output
        assert(output.contains("Hello, Jenish!"))
    }

    @Test
    fun `goodBye should print goodbye message`() {
        // Create user
        val user = User("Jenish", 0)

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        user.goodBye()
        val output = outputStream.toString()

        // Assertions for correct output
        assert(output.contains("Goodbye, Jenish!"))
    }

    @Test
    fun `printStats should print correct balance and owed amounts`() {
        // Create users
        val user1 = User("John", 1000)
        val user2 = User("Alice", 200)

        // Setup owed lists
        val owedFrom = mutableListOf(Pair(user2, 100))
        val owedTo = mutableListOf(Pair(user1, 150))

        // Update user1's owedFrom and owedTo
        val userWithOwedAmounts = user1.copy(owedFrom = owedFrom, owedTo = owedTo)

        // Capture the output of printStats
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        userWithOwedAmounts.printStats()

        // Verify the output
        val output = outputStream.toString()

        // Assertions for correct output
        assert(output.contains("Your balance is $1000"))
        assert(output.contains("Owed $150 to John"))
        assert(output.contains("Owed $100 from Alice"))

        // Reset System.out
        System.setOut(System.out)
    }

    @Test
    fun `printStats should print nothing when there is no owed amount`() {
        // Create user with no owed amounts
        val user = User("John", 1000)

        // Capture the output of printStats
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        user.printStats()

        // Verify the output
        val output = outputStream.toString()

        // Assert the balance is printed, but no owed amounts
        assert(output.contains("Your balance is $1000"))
        assert(!output.contains("Owed"))

        // Reset System.out
        System.setOut(System.out)
    }

    @Nested
    inner class Withdraw {
        @Test
        fun `Should decrease balance`() {
            val alice = AccountService.getUser("Alice")!!
            alice.balance = 200
            alice.withdraw(150)

            assertEquals(50, alice.balance)
        }

        @Test
        fun `Should throw exception if amount exceeds balance`() {
            val alice = AccountService.getUser("Alice")!!
            alice.balance = 200
            val exception = assertThrows(Exception::class.java) {
                alice.withdraw(1200)
            }

            assertEquals("Insufficient funds", exception.message)
        }

        @Test
        fun `Should throw exception for negative amount`() {
            val alice = AccountService.getUser("Alice")!!
            alice.balance = 200
            val exception = assertThrows(Exception::class.java) {
                alice.withdraw(-100)
            }

            assertEquals("Amount must be positive", exception.message)
        }
    }

    @Nested
    inner class Deposit {
        @Test
        fun `Should throw exception for negative amount`() {
            val alice = AccountService.getUser("Alice")!!
            alice.balance = 200
            val exception = assertThrows(Exception::class.java) {
                alice.deposit(-100)
            }

            assertEquals("Amount must be positive", exception.message)
        }

        @Test
        fun `Should increase balance`() {
            val alice = AccountService.getUser("Alice")!!
            alice.balance = 200
            alice.deposit(150)

            assertEquals(350, alice.balance)
        }

        @Test
        fun `deposit should increase balance and handle debts`() {
            val alice = AccountService.getUser("Alice")!!
            val bob = AccountService.getUser("Bob")!!
            alice.owedTo.add(Pair(bob, 100))
            bob.owedFrom.add(Pair(alice, 100))


            alice.deposit(200)

            assertEquals(100, alice.balance)
            assertEquals(100, bob.balance)
            assertEquals(mutableListOf<Pair<User, Int>>(), alice.owedTo)
            assertEquals(mutableListOf<Pair<User, Int>>(), bob.owedFrom)
        }
    }

    @Nested
    inner class Transfer {

        @BeforeEach
        fun setup() {
            val alice = AccountService.getUser("Alice")!!
            val bob = AccountService.getUser("Bob")!!
            alice.balance = 110
            bob.balance = 35
        }

        @Test
        fun `transfer should throw exception for negative amount`() {
            val alice = AccountService.getUser("Alice")!!
            val bob = AccountService.getUser("Bob")!!
            val exception = assertThrows(Exception::class.java) {
                alice.transfer(bob, -100)
            }

            assertEquals("Amount must be positive", exception.message)
        }

        @Test
        fun `transfer should reduce balance and increase recipient balance`() {
            val alice = AccountService.getUser("Alice")!!
            val bob = AccountService.getUser("Bob")!!

            AccountService.loggedInUser = alice
            alice.transfer(bob, 50)

            assertEquals(60, alice.balance)
            assertEquals(85, bob.balance)
        }

        @Test
        fun `transfer should handle partial payment of debts`() {
            val alice = AccountService.getUser("Alice")!!
            val bob = AccountService.getUser("Bob")!!
            bob.balance = 0
            bob.owedTo.add(Pair(alice, 20))
            alice.owedFrom.add(Pair(bob, 20))
            alice.transfer(bob, 25)

            assertEquals(5, bob.balance)
            assertEquals(mutableListOf<Pair<User, Int>>(), alice.owedFrom)
            assertEquals(mutableListOf<Pair<User, Int>>(), bob.owedTo)
        }
    }
}