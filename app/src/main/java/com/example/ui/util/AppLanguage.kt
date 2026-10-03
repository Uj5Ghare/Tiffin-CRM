package com.example.ui.util

enum class AppLanguage(
    val code: String,
    val displayName: String,
    val nativeName: String,
    val flag: String
) {
    ENGLISH("en", "English", "English", "🇬🇧"),
    HINDI("hi", "Hindi", "हिंदी", "🇮🇳"),
    MARATHI("mr", "Marathi", "मराठी", "🚩"),
    GUJARATI("gu", "Gujarati", "ગુજરાતી", "🦁")
}
