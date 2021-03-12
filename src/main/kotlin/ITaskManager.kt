import java.util.*

interface ITaskManager {
    val maxCapacity: Int

    fun addProcess(process: Process, createdAt: Date = Date())
    fun killProcess(pid: Int)
    fun killProcesses()
    fun killProcesses(priority: ProcessPriority)
    fun listProcesses(sortBy: SortOptions = SortOptions.CREATED_AT): List<Process>
    val size: Int
}

