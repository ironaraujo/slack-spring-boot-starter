package com.kreait.slack.api.test.group.im

import com.kreait.slack.api.contract.jackson.group.im.ErrorImRepliesResponse
import com.kreait.slack.api.contract.jackson.group.im.ImRepliesRequest
import com.kreait.slack.api.contract.jackson.group.im.SuccessfulImRepliesResponse
import com.kreait.slack.api.group.ApiCallResult
import com.kreait.slack.api.group.im.ImMethodGroup
import com.kreait.slack.api.group.im.ImRepliesMethod
import com.kreait.slack.api.test.MockMethod

/**
 * Testable implementation of [ImMethodGroup.replies]
 */
class MockImRepliesMethod : ImRepliesMethod(), MockMethod<SuccessfulImRepliesResponse, ErrorImRepliesResponse, ImRepliesRequest> {

    override fun params(): ImRepliesRequest = params

    override var successResponse: SuccessfulImRepliesResponse? = null
    override var failureResponse: ErrorImRepliesResponse? = null

    override fun request(): ApiCallResult<SuccessfulImRepliesResponse, ErrorImRepliesResponse> {

        this.successResponse?.let { this.onSuccess?.invoke(it) }
        this.failureResponse?.let { this.onFailure?.invoke(it) }

        return ApiCallResult(this.successResponse, this.failureResponse)
    }
}
