import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PriorityTaskManagerTest {

    @Test
    fun `adding process succeeds when manager is under capacity`() {
        val taskManager = TaskManager(2, PriorityStrategy)
        taskManager.addProcess(Process(0, ProcessPriority.LOW))

        assertEquals(1, taskManager.size)
    }

    @Test
    fun `new process replaces lowest priority process when task manager is at capacity`() {
        val taskManager = TaskManager(2, PriorityStrategy)

        val processes = listOf(
            Process(0, ProcessPriority.LOW),
            Process(1, ProcessPriority.HIGH),
            Process(2, ProcessPriority.HIGH),
            Process(3, ProcessPriority.LOW),
        )

        taskManager.addProcess(processes[0])
        taskManager.addProcess(processes[1])
        taskManager.addProcess(processes[2])

        assertThrows<TaskManagerAtCapacityException> {
            taskManager.addProcess(processes[3])
        }

        assertEquals(listOf(processes[1], processes[2]), taskManager.listProcesses())

    }
}