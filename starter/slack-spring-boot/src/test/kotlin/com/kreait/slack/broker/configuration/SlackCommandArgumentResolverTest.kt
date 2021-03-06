package com.kreait.slack.broker.configuration

import com.kreait.slack.api.contract.jackson.SlackCommand
import com.kreait.slack.api.contract.jackson.sample
import com.kreait.slack.api.contract.jackson.toParameterMap
import com.kreait.slack.broker.RequestTestUtils
import com.kreait.slack.broker.RequestTestUtils.mockMethodParameter
import com.kreait.slack.broker.RequestTestUtils.mockNativeWebRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Instant

internal class SlackCommandArgumentResolverTest {

    @Test
    fun supportsParameter() {
        Assertions.assertTrue(SlackCommandArgumentResolver("")
                .supportsParameter(mockMethodParameter(SlackCommand::class.java, Command::class.java)))

        Assertions.assertFalse(SlackCommandArgumentResolver("")
                .supportsParameter(mockMethodParameter(SlackCommand::class.java, RequestTestUtils.TestAnnotation::class.java)))

        Assertions.assertFalse(SlackCommandArgumentResolver("")
                .supportsParameter(mockMethodParameter(Any::class.java, Command::class.java)))
    }

    @Test
    fun internalResolveArgument() {
        //setup
        val command = SlackCommand.sample()
        val signingSecret = "mySecret"
        val timestamp = Instant.now()

        val mockNativeWebRequest = mockNativeWebRequest(timestamp, signingSecret, command.toParameterMap())

        //test
        val resolvedArgument = SlackCommandArgumentResolver(signingSecret)
                .resolveArgument(mockMethodParameter(SlackCommand::class.java, Command::class.java), null, mockNativeWebRequest, null)

        Assertions.assertEquals(command, resolvedArgument)
    }

}
