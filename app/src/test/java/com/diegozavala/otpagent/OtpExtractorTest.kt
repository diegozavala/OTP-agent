package com.diegozavala.otpagent

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class OtpExtractorTest {

    @Test
    fun `extracts OTP from English message`() {
        assertEquals("482910", OtpExtractor.extract("Your OTP is 482910"))
    }

    @Test
    fun `extracts code from verification message`() {
        assertEquals("7381", OtpExtractor.extract("Verification code: 7381"))
    }

    @Test
    fun `extracts 8-digit code`() {
        assertEquals("38291740", OtpExtractor.extract("Your code is 38291740"))
    }

    @Test
    fun `extracts code from Spanish verification message`() {
        assertEquals("583920", OtpExtractor.extract("Tu código de verificación es 583920"))
    }

    @Test
    fun `extracts code from Spanish clave message`() {
        assertEquals("4829", OtpExtractor.extract("Clave temporal: 4829. Válida por 5 minutos"))
    }

    @Test
    fun `extracts code from Spanish security code message`() {
        assertEquals("291048", OtpExtractor.extract("Su código de seguridad es 291048"))
    }

    @Test
    fun `extracts code from Spanish contraseña message`() {
        assertEquals("839201", OtpExtractor.extract("Tu contraseña temporal es 839201"))
    }

    @Test
    fun `extracts code from Spanish confirmar message`() {
        assertEquals("5829", OtpExtractor.extract("Para confirmar su cuenta ingrese 5829"))
    }

    @Test
    fun `returns null for generic English message`() {
        assertNull(OtpExtractor.extract("You have 3 new messages"))
    }

    @Test
    fun `returns null for generic Spanish message`() {
        assertNull(OtpExtractor.extract("Tienes 3 mensajes nuevos"))
    }

    @Test
    fun `returns null for address with year-like number`() {
        assertNull(OtpExtractor.extract("Meeting at 2025 Main St"))
    }

    @Test
    fun `returns null for order number without keyword`() {
        assertNull(OtpExtractor.extract("Order #12345 shipped"))
    }

    @Test
    fun `returns null for empty string`() {
        assertNull(OtpExtractor.extract(""))
    }

    @Test
    fun `ignores short numbers under 4 digits`() {
        assertNull(OtpExtractor.extract("Your code expires in 300 seconds"))
    }

    @Test
    fun `picks first valid code when multiple present`() {
        assertEquals("482910", OtpExtractor.extract("Your code is 482910. Expires in 300 seconds."))
    }

    @Test
    fun `handles Spanish without accents`() {
        assertEquals("192837", OtpExtractor.extract("Tu codigo de verificacion es 192837"))
    }
}
