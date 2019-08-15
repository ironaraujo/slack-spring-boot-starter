package com.kreait.slack.broker.broker

import com.kreait.slack.api.contract.jackson.InteractiveComponentResponse
import com.kreait.slack.api.contract.jackson.sample
import com.kreait.slack.broker.extensions.sample
import com.kreait.slack.broker.metrics.InteractiveComponentMetrics
import com.kreait.slack.broker.receiver.InteractiveComponentReceiver
import com.kreait.slack.broker.store.InMemoryTeamStore
import com.kreait.slack.broker.store.Team
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import java.util.concurrent.atomic.AtomicInteger

@DisplayName("Event Broker Tests")
class InteractiveComponentBrokerTests {
    @Test
    @DisplayName("exceptions are thrown at the end of the execution")
    fun shouldThrow() {
        val teamStore = InMemoryTeamStore()
        teamStore.put(Team.sample().copy(teamId = "TestId"))
        val sampleEvent = InteractiveComponentResponse.sample().copy(team = InteractiveComponentResponse.Team.sample().copy(id = "TestId"))
        Assertions.assertThrows(Exception::class.java) {
            InteractiveComponentBroker(listOf(ShouldThrowReceiver(), ShouldThrowReceiver()), teamStore).receiveEvents(sampleEvent, HttpHeaders.EMPTY)
        }
    }

    class ShouldThrowReceiver : InteractiveComponentReceiver {
        override fun shouldThrowException(exception: Exception): Boolean {
            return true
        }

        override fun onReceiveInteractiveMessage(interactiveComponentResponse: InteractiveComponentResponse, headers: HttpHeaders, team: Team) {
            throw Exception()
        }
    }

    @Test
    @DisplayName("Broker Test")
    fun brokerTest() {

        val teamStore = InMemoryTeamStore()

        teamStore.put(Team.sample().copy(teamId = "TestId"))

        val metrics = InteractiveComponentMetrics()

        val meterRegistry = SimpleMeterRegistry()
        metrics.bindTo(meterRegistry)

        val successReceiver = SuccessReceiver()
        val errorReceiver = ErrorReceiver()

        val componentResponse = InteractiveComponentResponse.sample().copy(team = InteractiveComponentResponse.Team.sample().copy(id = "TestId"))

        InteractiveComponentBroker(listOf(successReceiver, errorReceiver), teamStore, metrics).receiveEvents(componentResponse, HttpHeaders.EMPTY)

        Assertions.assertTrue(successReceiver.executed)
        Assertions.assertTrue(errorReceiver.executed)

        Assertions.assertEquals(1.0, meterRegistry.get("slack.interactive.received").counter().count())
        Assertions.assertEquals(2.0, meterRegistry.get("slack.interactive.receiver.executions").counter().count())
        Assertions.assertEquals(1.0, meterRegistry.get("slack.interactive.receiver.errors").counter().count())
    }


    @Test
    @DisplayName("Broker Test Without Metrics")
    fun brokerTestWithoutMetrics() {

        val teamStore = InMemoryTeamStore()

        teamStore.put(Team.sample().copy(teamId = "TestId"))

        val successReceiver = SuccessReceiver()
        val errorReceiver = ErrorReceiver()

        val componentResponse = InteractiveComponentResponse.sample().copy(team = InteractiveComponentResponse.Team.sample().copy(id = "TestId"))

        InteractiveComponentBroker(listOf(successReceiver, errorReceiver), teamStore).receiveEvents(componentResponse, HttpHeaders.EMPTY)

        Assertions.assertTrue(successReceiver.executed)
        Assertions.assertTrue(errorReceiver.executed)
    }

    @Test
    @DisplayName("Test InteractiveComponentReceiver order")
    fun testComponentReceiverExecutionOrder() {
        val atomic = AtomicInteger(0)
        val first = FirstComponentReceiver(atomic)
        val second = SecondComponentReceiver(atomic)
        val third = ThirdComponentReceiver(atomic)
        val event = InteractiveComponentResponse.sample()
                .copy(team = InteractiveComponentResponse.Team.sample().copy(id = "TestTeamId"))
        val store = InMemoryTeamStore()
        store.put(Team.sample().copy(teamId = "TestTeamId"))

        InteractiveComponentBroker(listOf(third, second, first), store)
                .receiveEvents(event, HttpHeaders.EMPTY)

        Assertions.assertEquals(0, first.currentOrder)
        Assertions.assertEquals(1, second.currentOrder)
        Assertions.assertEquals(2, third.currentOrder)
    }

    class FirstComponentReceiver(private val current: AtomicInteger) : InteractiveComponentReceiver {
        var currentOrder: Int? = null

        override fun order(): Int {
            return 1
        }

        override fun onReceiveInteractiveMessage(interactiveComponentResponse: InteractiveComponentResponse, headers: HttpHeaders, team: Team) {
            currentOrder = current.getAndIncrement()
        }
    }

    class SecondComponentReceiver(private val current: AtomicInteger) : InteractiveComponentReceiver {
        var currentOrder: Int? = null
        override fun order(): Int {
            return 2
        }

        override fun onReceiveInteractiveMessage(interactiveComponentResponse: InteractiveComponentResponse, headers: HttpHeaders, team: Team) {
            currentOrder = current.getAndIncrement()
        }
    }

    class ThirdComponentReceiver(private val current: AtomicInteger) : InteractiveComponentReceiver {
        var currentOrder: Int? = null
        override fun order(): Int {
            return 3
        }

        override fun onReceiveInteractiveMessage(interactiveComponentResponse: InteractiveComponentResponse, headers: HttpHeaders, team: Team) {
            currentOrder = current.getAndIncrement()
        }
    }


    class SuccessReceiver : InteractiveComponentReceiver {
        var executed: Boolean = false

        override fun onReceiveInteractiveMessage(interactiveComponentResponse: InteractiveComponentResponse, headers: HttpHeaders, team: Team) {
            executed = true
        }
    }

    class ErrorReceiver : InteractiveComponentReceiver {
        var executed: Boolean = false

        override fun onReceiveInteractiveMessage(interactiveComponentResponse: InteractiveComponentResponse, headers: HttpHeaders, team: Team) {
            executed = true
            throw IllegalStateException("Failing Test Case")
        }
    }
}