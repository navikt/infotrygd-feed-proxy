package no.nav.infotrygd.feed.proxy.security

import no.nav.security.token.support.core.context.TokenValidationContext
import no.nav.security.token.support.core.context.TokenValidationContextHolder
import no.nav.security.token.support.core.exceptions.JwtTokenInvalidClaimException
import no.nav.security.token.support.core.exceptions.JwtTokenMissingException
import org.springframework.stereotype.Component

@Component
class StsValidator(private val tokenValidationContextHolder: TokenValidationContextHolder) {

    fun validateSts(subject: String) {
        val context = tokenValidationContextHolder.tokenValidationContext
            ?: error("tokenValidationContext kan ikke være null")
        if (context.validateIssuerWithSubject("sts", subject)) {
            return
        }
        throw JwtTokenMissingException("Savner gyldig issuer")
    }
}

private fun TokenValidationContext.validateIssuerWithSubject(issuerName: String, subject: String): Boolean {
    val sts = this.getClaims(issuerName)
    if (sts != null) {
        if (sts.subject == subject) {
            return true
        } else {
            throw JwtTokenInvalidClaimException("Subject er ikke gyldig")
        }
    }
    return false
}
