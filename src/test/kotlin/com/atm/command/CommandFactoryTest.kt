package com.atm.command

import org.example.com.atm.command.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.test.assertFailsWith

class CommandFactoryTest {

  private val commandFactory = CommandFactory()
  
  @Test
  fun `valid transaction type - Login`() {
   assertEquals(LoginCommand::class.java, commandFactory.getCommand("Login").javaClass)
  }

  @Test
  fun `valid transaction type - Deposit`() {
   assertEquals(DepositCommand::class.java, commandFactory.getCommand("Deposit").javaClass)
  }

  @Test
  fun `valid transaction type - Withdraw`() {
   assertEquals(WithdrawCommand::class.java, commandFactory.getCommand("Withdraw").javaClass)
  }

  @Test
  fun `valid transaction type - Transfer`() {
   assertEquals(TransferCommand::class.java, commandFactory.getCommand("Transfer").javaClass)
  }

  @Test
  fun `valid transaction type - Logout`() {
   assertEquals(LogoutCommand::class.java, commandFactory.getCommand("Logout").javaClass)
  }

  @Test
  fun `getCommand should throw exception for invalid command`() {
   assertFailsWith<Exception> {
    commandFactory.getCommand("invalidCommand")
   }
  }

 @Test
 fun `getCommand should be case-insensitive`() {
  val command = commandFactory.getCommand("LoGiN")
  assertNotNull(command)
  assert(command is LoginCommand)
 }
}