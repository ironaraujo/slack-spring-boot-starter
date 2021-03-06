package com.kreait.slack.api.group.users

import com.kreait.slack.api.contract.jackson.group.users.ErrorSetPresenceResponse
import com.kreait.slack.api.contract.jackson.group.users.SetPresenceRequest
import com.kreait.slack.api.contract.jackson.group.users.SuccessfulSetPresenceResponse
import com.kreait.slack.api.group.ApiCallMethod

/**
 *
 * Abstract representation of an slack api operation
 * https://api.slack.com/methods/users.setPresence
 */
@Suppress("UNCHECKED_CAST")
abstract class UsersSetPresenceMethod : ApiCallMethod<UsersSetPresenceMethod, SuccessfulSetPresenceResponse, ErrorSetPresenceResponse, SetPresenceRequest>()
