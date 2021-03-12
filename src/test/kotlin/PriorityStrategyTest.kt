import ProcessPriority.*
import org.junit.Test

import org.junit.Assert.*
import java.util.*

class PriorityStrategyTest {

    @Test
    fun `finds process to be removed when there is one process with lower priority`() {
        val strategy = PriorityStrategy()

        val processes = listOf(
            ProcessData(Process(3, MEDIUM), Date(20)),
            ProcessData(Process(7, LOW), Date(1)),
        )

        val pid = strategy.findProcessToKill(processes, Process(99, MEDIUM))

        assertEquals(7, pid)
    }

    @Test
    fun `finds process to be removed when there is many processes with lowest priority`() {
        val strategy = PriorityStrategy()

        val processes = listOf(
            ProcessData(Process(3, MEDIUM), Date(20)),
            ProcessData(Process(7, LOW), Date(10)),
            ProcessData(Process(9, LOW), Date(2)),
            ProcessData(Process(11, LOW), Date(3))
        )

        val pid = strategy.findProcessToKill(processes, Process(99, MEDIUM))

        assertEquals(9, pid)
    }

    @Test
    fun `does not find process to be removed if there is no process with lower priority`() {
        val strategy = PriorityStrategy()

        val processes = listOf(
            ProcessData(Process(3, HIGH), Date(20)),
            ProcessData(Process(7, HIGH), Date(1)),
            ProcessData(Process(9, HIGH), Date(2))
        )

        val pid = strategy.findProcessToKill(processes, Process(99, HIGH))

        assertNull(pid)
    }
}