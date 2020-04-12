package com.pes.pockles.data.api

import com.pes.pockles.data.TokenManager
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject


class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager
) : Authenticator {

    companion object {
        const val AUTH_HEADER_NAME = "Authorization"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        Timber.d("Intercepted authenticate call with code: %d", response.code())
        if (response.code() == 401) {
            tokenManager.refreshToken()
        }
        return response
            .request()
            .newBuilder()
            .header(AUTH_HEADER_NAME, wrapToken(tokenManager.token))
            .build()
    }

    private fun wrapToken(token: String?): String {
        return "Bearer $token"
    }

}
