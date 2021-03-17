import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class FifoStrategyTest {

    @Test
    fun findProcessToKill() {
        val processes = listOf(
            ProcessData(Process(2), Date(20)),
            ProcessData(Process(5), Date(1)),
            ProcessData(Process(3), Date(2))
        )

        val result = FifoStrategy.findProcessToKill(processes)

        assertEquals(5, result)
    }
}