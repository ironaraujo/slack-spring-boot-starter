package com.kreait.slack.api.test.group.chat

import com.kreait.slack.api.contract.jackson.group.chat.ErrorPostMessageResponse
import com.kreait.slack.api.contract.jackson.group.chat.SlackPostMessageRequest
import com.kreait.slack.api.contract.jackson.group.chat.SuccessfulPostMessageResponse
import com.kreait.slack.api.group.ApiCallResult
import com.kreait.slack.api.group.chat.ChatPostMessageMethod
import com.kreait.slack.api.test.MockMethod

open class MockChatPostMessage : ChatPostMessageMethod(), MockMethod<SuccessfulPostMessageResponse, ErrorPostMessageResponse, SlackPostMessageRequest> {

    override fun params(): SlackPostMessageRequest {
        return params;
    }

    override var successResponse: SuccessfulPostMessageResponse? = null
    override var failureResponse: ErrorPostMessageResponse? = null

    override fun request(): ApiCallResult<SuccessfulPostMessageResponse, ErrorPostMessageResponse> {
        this.successResponse?.let { this.onSuccess?.invoke(it) }
        this.failureResponse?.let { this.onFailure?.invoke(it) }

        return ApiCallResult(this.successResponse, this.failureResponse)
    }


}
