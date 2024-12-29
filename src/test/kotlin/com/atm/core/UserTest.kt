package com.atm.core

import org.example.com.atm.core.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class UserTest {
  @Test
  fun `User should be correctly initialized`() {
   val user = User("John", 1000)

   assertEquals("John", user.name)
   assertEquals(1000, user.balance)
   assertEquals(emptyList<Pair<User, Int>>(), user.owedFrom)
   assertEquals(emptyList<Pair<User, Int>>(), user.owedTo)
  }

  @Test
  fun `printStats should print correct balance and owed amounts`() {
   // Create users
   val user1 = User("John", 1000)
   val user2 = User("Alice", 200)

   // Setup owed lists
   val owedFrom = listOf(Pair(user2, 100))
   val owedTo = listOf(Pair(user1, 150))

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

 }