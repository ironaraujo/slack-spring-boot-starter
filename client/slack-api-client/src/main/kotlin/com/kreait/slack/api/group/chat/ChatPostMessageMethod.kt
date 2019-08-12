package com.kreait.slack.api.group.chat

import com.kreait.slack.api.contract.jackson.group.chat.ErrorPostMessageResponse
import com.kreait.slack.api.contract.jackson.group.chat.SlackPostMessageRequest
import com.kreait.slack.api.contract.jackson.group.chat.SuccessfulPostMessageResponse
import com.kreait.slack.api.group.ApiCallMethod

abstract class ChatPostMessageMethod : ApiCallMethod<ChatPostMessageMethod, SuccessfulPostMessageResponse, ErrorPostMessageResponse, SlackPostMessageRequest>() {

}
