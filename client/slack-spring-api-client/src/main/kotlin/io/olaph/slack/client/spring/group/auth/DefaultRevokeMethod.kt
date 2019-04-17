package io.olaph.slack.client.spring.group.auth

import io.olaph.slack.client.UnknownResponseException
import io.olaph.slack.client.group.ApiCallResult
import io.olaph.slack.client.group.auth.AuthRevokeMethod
import io.olaph.slack.client.spring.group.SlackRequestBuilder
import io.olaph.slack.dto.jackson.group.auth.ErrorAuthRevokeResponse
import io.olaph.slack.dto.jackson.group.auth.SlackAuthRevokeResponse
import io.olaph.slack.dto.jackson.group.auth.SuccessfulAuthRevokeResponse
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestTemplate


class DefaultRevokeMethod(private val authToken: String, private val restTemplate: RestTemplate = RestTemplate(BufferingClientHttpRequestFactory(SimpleClientHttpRequestFactory()))) : AuthRevokeMethod() {

    override fun request(): ApiCallResult<SuccessfulAuthRevokeResponse, ErrorAuthRevokeResponse> {

        val response = SlackRequestBuilder<SlackAuthRevokeResponse>(authToken, restTemplate)
                .toMethod("auth.revoke")
                .returnAsType(SlackAuthRevokeResponse::class.java)
                .postUrlEncoded(this.params.toRequestMap())

        return when {
            response.body is SuccessfulAuthRevokeResponse -> {
                val responseEntity = response.body as SuccessfulAuthRevokeResponse
                this.onSuccess?.invoke(responseEntity)
                ApiCallResult(success = responseEntity)
            }
            response.body is ErrorAuthRevokeResponse -> {
                val responseEntity = response.body as ErrorAuthRevokeResponse
                this.onFailure?.invoke(responseEntity)
                ApiCallResult(failure = responseEntity)
            }
            else -> {
                throw UnknownResponseException(this::class, response)
            }
        }
    }
}
