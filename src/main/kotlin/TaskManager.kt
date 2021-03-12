import java.util.*


class TaskManager(override val maxCapacity: Int, private val strategy: Strategy? = null) :
    ITaskManager {

    private val processes = HashMap<Int, ProcessData>()

    override fun addProcess(process: Process, createdAt: Date) {
        if (processes.size == maxCapacity) {
            addAtCapacity(process, createdAt, strategy)
        } else {
            processes[process.pid] = ProcessData(process, createdAt)
        }
    }

    private fun addAtCapacity(process: Process, createdAt: Date, strategy: Strategy?) {
        if (strategy == null) {
            throw TaskManagerAtCapacityException()
        } else {
            val toBeReplaced = strategy.findProcessToKill(processes.values, process)

            if (toBeReplaced != null) {
                killProcess(toBeReplaced)
                processes[process.pid] = ProcessData(process, createdAt)
            }
        }
    }

    private fun findByPriority(priority: ProcessPriority): List<Int> {
        return processes.values
            .filter { it.process.priority == priority }
            .map { it.process.pid }
    }

    override fun killProcess(pid: Int) {
        processes[pid]?.process?.kill()
        processes.remove(pid)
    }

    override fun killProcesses(priority: ProcessPriority) {
        val processIds = findByPriority(priority)
        processIds.forEach { killProcess(it) }
    }

    override fun killProcesses() {
        val processIds =  processes.keys.toList()
        processIds.forEach { killProcess(it) }
    }

    override fun listProcesses(sortBy: SortOptions): List<Process> {
        val comparator: Comparator<ProcessData> = when (sortBy) {
            SortOptions.CREATED_AT -> compareBy { it.createdAt }
            SortOptions.PRIORITY -> compareBy { it.process.priority }
            SortOptions.PID -> compareBy { it.process.pid }
        }

        return processes.values.asSequence()
            .sortedWith(comparator)
            .map { it.process }.toList()
    }

    override val size: Int
        get() = processes.size

}
