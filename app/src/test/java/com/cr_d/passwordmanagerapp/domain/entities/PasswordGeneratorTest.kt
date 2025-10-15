package com.cr_d.passwordmanagerapp.domain.entities

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PasswordGeneratorTest {

    @Test
    fun `should create a default password`(){
        val testSubject = PasswordGenerator()

        Assertions.assertTrue(testSubject.hasLowerCase)
        Assertions.assertFalse(testSubject.hasUpperCase)
        Assertions.assertFalse(testSubject.hasNumbers)
        Assertions.assertFalse(testSubject.hasSpecials)
        Assertions.assertEquals(8, testSubject.minLength)
        Assertions.assertEquals(20, testSubject.maxLength)
    }

    @Test
    fun `should return a password on generatePassword Method`(){
        val testSubject = PasswordGenerator().generatePassword()

        Assertions.assertEquals("ASDF", testSubject)
    }
}