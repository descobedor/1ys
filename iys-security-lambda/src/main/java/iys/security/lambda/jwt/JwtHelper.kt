package iys.security.lambda.jwt

import io.jsonwebtoken.Jwts
import java.lang.System.currentTimeMillis
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.Base64.getEncoder

class JwtHelper {

    fun generateToken(subject: String, expiration: Date, additionalClaims: Map<String, Any> = emptyMap()): String {
        return Jwts.builder()
            .setClaims(additionalClaims)
            .setSubject(subject)
            .setIssuedAt(Date(currentTimeMillis()))
            .setExpiration(expiration)
          //œ  .signWith(signingKey)
            .compact()
    }

    private fun addTimeUnixTimestamp(duration: Duration) : (Long) -> Long = {
        unixTimestamp -> Instant.ofEpochSecond(unixTimestamp).plus(duration).toEpochMilli()
    }

    fun getJsonWebToken(clientId: String?, scope: String?, duration: Duration): String {

        println(generateToken("sub", Date.from(Instant.now())));

        val header = """{
    alg: "RS256",
    typ: "JWT"
}"""
        val payload = """{
    exp: ${(addTimeUnixTimestamp(duration))(currentTimeMillis())},
    iat: ${(addTimeUnixTimestamp(Duration.ofHours(0)))(currentTimeMillis())},
    iss: '',
    client_id: $clientId
    scope: $scope
}"""
        println(header)
        println(payload)

        val jwt_parts = """{header: ${getEncoder().encodeToString(header.encodeToByteArray())},
payload: ${getEncoder().encodeToString(payload.encodeToByteArray())}}"""

        val params = """{
            KeyId: "KMS KEY ID",
        }"""

        return jwt_parts
    }
}

