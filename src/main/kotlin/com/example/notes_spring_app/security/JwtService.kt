package com.example.notes_spring_app.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Base64
import java.util.Date

@Service
class JwtService(
    @field:Value("JWT_BASE64") private val jwtBase64: String
) {
    private val jwtKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtBase64))
    private val accessTokenValidity = 15L * 60L * 1000L
    private val refreshTokenValidity =  30L * 24L * 60L * 60L * 1000L

    private fun generateToken(
        userId: String,
        type: String,
        expiry: Long
    ): String {
        val now = Date()
        val expiryDate = Date(now.time + expiry)
        return Jwts.builder()
            .subject(userId)
            .claim("token_type", type)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(jwtKey)
            .compact()
    }

    fun generateAccessToken(userId: String) : String {
        return generateToken(userId, "access", accessTokenValidity)
    }

    fun generateRefreshToken(userId: String) : String {
        return generateToken(userId, "refresh", refreshTokenValidity)
    }

    private fun parseAllClaims(token: String): Claims? {
        val rawToken = if (token.startsWith("Bearer")) {
            token.removePrefix("Bearer ") // Authorization: Bearer <token>
        } else token
        return try {
            Jwts.parser()
                .verifyWith(jwtKey)
                .build()
                .parseSignedClaims(rawToken)
                .payload
        } catch (e: Exception) {
            null
        }
    }

    private fun validateToken(token: String, expectedTokenType: String): Boolean {
        val claims = parseAllClaims(token) ?: return false
        val actualTokenType = claims["token_type"] as? String ?: return false
        return actualTokenType == expectedTokenType
    }

    fun validateAccessToken(token: String): Boolean {
        return validateToken(token, "access")
    }

    fun validateRefreshToken(token: String): Boolean {
        return validateToken(token, "refresh")
    }


    fun getUserIdFromToken(token: String): String {
        val claims = parseAllClaims(token) ?: throw IllegalArgumentException("Invalid token")
        return claims.subject
    }
}