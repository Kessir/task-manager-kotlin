import org.junit.Test

import org.junit.Assert.*
import java.util.*

class FifoStrategyTest {

    @Test
    fun findProcessToKill() {
        val strategy = FifoStrategy()

        val processes = listOf(
            ProcessData(Process(2), Date(20)),
            ProcessData(Process(5), Date(1)),
            ProcessData(Process(3), Date(2))
        )

        val result = strategy.findProcessToKill(processes)

        assertEquals(5, result)
    }
}