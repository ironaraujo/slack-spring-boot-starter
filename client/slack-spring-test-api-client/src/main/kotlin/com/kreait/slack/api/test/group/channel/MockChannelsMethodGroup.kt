package com.kreait.slack.api.test.group.channel

import com.kreait.slack.api.group.channels.ChannelsHistoryMethod
import com.kreait.slack.api.group.channels.ChannelsJoinMethod
import com.kreait.slack.api.group.channels.ChannelsKickMethod
import com.kreait.slack.api.group.channels.ChannelsLeaveMethod
import com.kreait.slack.api.group.channels.ChannelsMarkMethod
import com.kreait.slack.api.group.channels.ChannelsMethodGroup
import com.kreait.slack.api.group.channels.ChannelsRenameMethod
import com.kreait.slack.api.group.channels.ChannelsRepliesMethod
import com.kreait.slack.api.group.channels.ChannelsSetPurposeMethod
import com.kreait.slack.api.group.channels.ChannelsUnarchiveMethod
import com.kreait.slack.api.group.channels.SetChannelsTopicMethod

class MockChannelsMethodGroup : ChannelsMethodGroup {

    private val mockChannelsInfoMethod = MockChannelsInfoMethod()
    private val mockChannelsArchiveMethod = MockChannelsArchiveMethod()
    private val mockChannelsCreateMethod = MockChannelsCreateMethod()
    private val mockChannelsInviteMethod = MockChannelsInviteMethod()
    private val mockChannelsSetPurposeMethod = MockChannelsSetPurposeMethod()

    override fun archive(authToken: String): MockChannelsArchiveMethod {
        return mockChannelsArchiveMethod
    }

    override fun create(authToken: String): MockChannelsCreateMethod {
        return mockChannelsCreateMethod
    }

    override fun history(authToken: String): ChannelsHistoryMethod {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun info(authToken: String): MockChannelsInfoMethod {
        return mockChannelsInfoMethod
    }

    override fun invite(authToken: String): MockChannelsInviteMethod {
        return mockChannelsInviteMethod
    }

    override fun join(authToken: String): ChannelsJoinMethod {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun kick(authToken: String): ChannelsKickMethod {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun leave(authToken: String): ChannelsLeaveMethod {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun mark(authToken: String): ChannelsMarkMethod {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun rename(authToken: String): ChannelsRenameMethod {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun replies(authToken: String): ChannelsRepliesMethod {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setPurpose(authToken: String): ChannelsSetPurposeMethod {
        return mockChannelsSetPurposeMethod
    }

    override fun setTopic(authToken: String): SetChannelsTopicMethod {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unarchive(authToken: String): ChannelsUnarchiveMethod {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}