package com.atm.command

import org.example.com.atm.command.CommandValidator
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
 class CommandValidatorTest {

  private val commandValidator = CommandValidator()
  
  @Test
  fun `valid transaction type - Login`() {
   assertTrue(commandValidator.validate("Login"))
  }

  @Test
  fun `valid transaction type - Deposit`() {
   assertTrue(commandValidator.validate("Deposit"))
  }

  @Test
  fun `valid transaction type - Withdraw`() {
   assertTrue(commandValidator.validate("Withdraw"))
  }

  @Test
  fun `valid transaction type - Transfer`() {
   assertTrue(commandValidator.validate("Transfer"))
  }

  @Test
  fun `valid transaction type - Logout`() {
   assertTrue(commandValidator.validate("Logout"))
  }

  @Test
  fun `case-insensitive validation`() {
   assertTrue(commandValidator.validate("login"))
   assertTrue(commandValidator.validate("DEPOSIT"))
   assertTrue(commandValidator.validate("WiThDrAw"))
  }

  @Test
  fun `invalid transaction type`() {
   assertFalse(commandValidator.validate("SignUp"))
   assertFalse(commandValidator.validate("InvalidCommand"))
   assertFalse(commandValidator.validate("12345"))
  }
}