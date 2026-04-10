package com.diegozavala.otpagent

object OtpExtractor {

    private val KEYWORDS = listOf(
        // English
        "otp", "code", "verification", "verify", "pin",
        "password", "passcode", "one-time", "one.time",
        "authentication", "2fa", "mfa", "token",
        "security code", "login code", "confirm",
        // Spanish
        "código", "verificación", "verificar", "clave",
        "contraseña", "confirmar", "autenticación",
        "código de seguridad", "código de acceso",
        "código de verificación", "código temporal",
        "un solo uso", "codigo", "verificacion",
        "contrasena", "autenticacion"
    )

    private val CODE_PATTERN = Regex("""\b(\d{4,8})\b""")

    private val YEAR_PATTERN = Regex("""\b(19|20)\d{2}\b""")

    fun extract(text: String): String? {
        val lowerText = text.lowercase()

        val hasKeyword = KEYWORDS.any { keyword -> lowerText.contains(keyword) }
        if (!hasKeyword) return null

        val candidates = CODE_PATTERN.findAll(text)
            .map { it.groupValues[1] }
            .filter { code ->
                !(code.length == 4 && YEAR_PATTERN.matches(code))
            }
            .toList()

        return candidates.firstOrNull()
    }
}
