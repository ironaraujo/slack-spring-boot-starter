package com.kreait.slack.api.group.reminders

import com.kreait.slack.api.contract.jackson.group.reminders.ErrorRemindersListResponse
import com.kreait.slack.api.contract.jackson.group.reminders.SuccessfulRemindersListResponse
import com.kreait.slack.api.group.ApiCallMethod

abstract class RemindersListMethod : ApiCallMethod<RemindersListMethod, SuccessfulRemindersListResponse, ErrorRemindersListResponse, Unit>()