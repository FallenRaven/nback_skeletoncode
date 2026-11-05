package mobappdev.example.nback_cimpl.ui.viewmodels

import android.content.Context
import kotlinx.coroutines.flow.emptyFlow
import mobappdev.example.nback_cimpl.data.UserPreferencesRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class GameVMTest {

    @Mock
    lateinit var userPreferencesRepository: UserPreferencesRepository

    @Mock
    lateinit var context: Context

    private lateinit var vm: GameVM

    @BeforeEach
    fun setUp() {
        whenever(userPreferencesRepository.highscore).thenReturn(emptyFlow())
        vm = GameVM(userPreferencesRepository, context)
    }

    @Test
    fun startGame() {
        assertEquals(-1, vm.gameState.value.eventValue)
        assertEquals(0, vm.gameState.value.currentEvent)

        vm.startGame()

        // After startGame, initial event is displayed (eventValue != -1) and currentEvent is 1
        assertNotEquals(-1, vm.gameState.value.eventValue)
        assertEquals(1, vm.gameState.value.currentEvent)
        assertTrue(vm.events.isNotEmpty())
    }

    @Test
    fun checkMatchScore() {
        // Given a 2-back sequence: pos 0 = 2, pos 1 = 5, pos 2 = 2 (matches pos 0!), pos 3 = 7 (does not match pos 1)
        vm.events = arrayOf(2, 5, 2, 7)
        assertEquals(0, vm.score.value)

        // At position 2: 2-back match exists (events[2] == events[0] == 2)
        vm.eventsPos = 2
        val matchResult = vm.checkMatch()

        assertTrue(matchResult, "checkMatch() should return true when current event matches event 2 steps back")
        assertEquals(1, vm.score.value, "Score should increment by 1 on a valid match")

        // At position 3: events[3] (7) != events[1] (5) -> not a match
        vm.eventsPos = 3
        val nonMatchResult = vm.checkMatch()

        assertFalse(nonMatchResult, "checkMatch() should return false when there is no match")
        assertEquals(1, vm.score.value, "Score should not change when there is no match")
    }

    @Test
    fun checkMatchEarlyPress() {
        vm.events = arrayOf(2, 5, 2, 7)

        // Before game starts (eventsPos = -1)
        vm.eventsPos = -1
        assertFalse(vm.checkMatch(), "checkMatch() should return false before game starts")

        // First event (eventsPos = 0 < nBack)
        vm.eventsPos = 0
        assertFalse(vm.checkMatch(), "checkMatch() should return false on the first event (not enough history)")

        // Second event (eventsPos = 1 < nBack)
        vm.eventsPos = 1
        assertFalse(vm.checkMatch(), "checkMatch() should return false on the second event for 2-back")

        assertEquals(0, vm.score.value, "Score should remain 0 for early presses")
    }

    @Test
    fun checkMatchDouble() {
        // Given a 2-back match at position 2
        vm.events = arrayOf(4, 8, 4, 1)
        vm.eventsPos = 2

        // First press should succeed and increment score
        val firstPress = vm.checkMatch()
        assertTrue(firstPress, "First press on a match should return true")
        assertEquals(1, vm.score.value)

        // Second press on the same event position must not be counted
        val secondPress = vm.checkMatch()
        assertFalse(secondPress, "Second press on the same event should return false")
        assertEquals(1, vm.score.value, "Score should not increment twice for the same event")
    }
}
