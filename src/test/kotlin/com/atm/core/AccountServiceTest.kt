package com.atm.core

import org.example.com.atm.core.AccountService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

 class AccountServiceTest {
  @Test
  fun `createUserIfNotExisting should create a new user if the user does not exist`() {
   // Create the user
   val user = AccountService.createUserIfNotExisting("John")

   // Check that the user is created and its balance is initialized to 0
   assertEquals("John", user.name)
   assertEquals(0, user.balance)
  }
 }