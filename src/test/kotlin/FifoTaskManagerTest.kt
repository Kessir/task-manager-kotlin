import org.junit.Test

import org.junit.Assert.*
import java.util.*

class FifoTaskManagerTest {

    @Test
    fun `adding process succeeds when manager is under capacity`() {
        val taskManager = TaskManager(3, FifoStrategy())
        taskManager.addProcess(Process(0, ProcessPriority.LOW))

        assertEquals(1, taskManager.size)
    }

    @Test
    fun `new process replaces oldest when task manager is at capacity`() {
        val taskManager = TaskManager(2, FifoStrategy())

        val processes = listOf<Process>(
            Process(0, ProcessPriority.LOW),
            Process(1, ProcessPriority.HIGH),
            Process(2, ProcessPriority.LOW),
            Process(3, ProcessPriority.LOW),
        )

        processes.forEach { taskManager.addProcess(it) }


        assertEquals(listOf(processes[2], processes[3]), taskManager.listProcesses())
    }
}