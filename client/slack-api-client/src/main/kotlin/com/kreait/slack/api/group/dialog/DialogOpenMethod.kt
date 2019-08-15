package com.kreait.slack.api.group.dialog

import com.kreait.slack.api.contract.jackson.group.dialog.ErrorOpenDialogResponse
import com.kreait.slack.api.contract.jackson.group.dialog.SlackOpenDialogRequest
import com.kreait.slack.api.contract.jackson.group.dialog.SuccessfulOpenDialogResponse
import com.kreait.slack.api.group.ApiCallMethod

abstract class DialogOpenMethod : ApiCallMethod<DialogOpenMethod, SuccessfulOpenDialogResponse, ErrorOpenDialogResponse, SlackOpenDialogRequest>()