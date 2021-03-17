import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class DefaultTaskManagerTest {

    @Test
    fun `adding process succeeds when manager is under capacity`() {
        val taskManager = TaskManager(5)
        taskManager.addProcess(Process(0, ProcessPriority.LOW))

        assertEquals(1, taskManager.size)
    }

    @Test
    fun `adding process fails when task manager is at capacity`() {
        val MAX_CAPACITY = 2
        val taskManager = TaskManager(MAX_CAPACITY)

        taskManager.addProcess(Process(0, ProcessPriority.LOW))
        taskManager.addProcess(Process(1, ProcessPriority.LOW))

        assertThrows<ProcessAdditionException> {
            taskManager.addProcess(Process(2, ProcessPriority.LOW))
        }

        assertEquals(MAX_CAPACITY, taskManager.size)
    }

    @Test
    fun `list processes sorted by createdAt`() {
        val taskManager = TaskManager(5)


        taskManager.addProcess(Process(0, ProcessPriority.HIGH), Date(1615451690))
        taskManager.addProcess(Process(1, ProcessPriority.LOW), Date(1615451691))
        taskManager.addProcess(Process(2, ProcessPriority.LOW), Date(1615451680))

        val result = taskManager.listProcesses(sortBy = SortOptions.CREATED_AT)

        assertEquals(2, result[0].pid)
        assertEquals(0, result[1].pid)
        assertEquals(1, result[2].pid)
    }

    @Test
    fun `list processes sorted by priority`() {
        val taskManager = TaskManager(5)

        taskManager.addProcess(Process(0, ProcessPriority.HIGH))
        taskManager.addProcess(Process(1, ProcessPriority.LOW))
        taskManager.addProcess(Process(2, ProcessPriority.LOW))
        taskManager.addProcess(Process(3, ProcessPriority.MEDIUM))

        val result = taskManager.listProcesses(sortBy = SortOptions.PRIORITY)

        val pids = result.map { it.pid }

        assertEquals(listOf(1, 2, 3, 0), pids)
    }

    @Test
    fun `list processes sorted by PID`() {
        val taskManager = TaskManager(5)

        taskManager.addProcess(Process(200))
        taskManager.addProcess(Process(101))
        taskManager.addProcess(Process(42))

        val result = taskManager.listProcesses(sortBy = SortOptions.PID)

        assertEquals(42, result[0].pid)
        assertEquals(101, result[1].pid)
        assertEquals(200, result[2].pid)
    }

    @Test
    fun `kill a single process`() {
        val taskManager = TaskManager(5)

        val processes = listOf(
            Process(0, ProcessPriority.LOW),
            Process(1, ProcessPriority.LOW),
            Process(2, ProcessPriority.HIGH),
        )

        for (process in processes) {
            taskManager.addProcess(process)
        }

        taskManager.killProcess(1)

        assertEquals(listOf(processes[0], processes[2]), taskManager.listProcesses())
    }

    @Test
    fun `kill all processes`() {
        val taskManager = TaskManager(5)

        val processes = listOf(
            Process(0, ProcessPriority.LOW),
            Process(1, ProcessPriority.LOW),
            Process(2, ProcessPriority.HIGH),
        )
        for (process in processes) taskManager.addProcess(process)

        taskManager.killProcesses()

        assertEquals(0, taskManager.size)
    }

    @Test
    fun `kill all processes of same priority`() {
        val taskManager = TaskManager(5)

        val processes = listOf(
            Process(0, ProcessPriority.LOW),
            Process(1, ProcessPriority.LOW),
            Process(2, ProcessPriority.MEDIUM),
            Process(3, ProcessPriority.HIGH),
        )

        for (process in processes) taskManager.addProcess(process)

        taskManager.killProcesses(ProcessPriority.LOW)

        assertEquals(listOf(processes[2], processes[3]), taskManager.listProcesses())
    }
}