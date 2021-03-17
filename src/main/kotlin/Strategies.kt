

interface Strategy {
    fun findProcessToKill(processes: Iterable<ProcessData>, process: Process = Process(-1)): Int?
}

object DefaultStrategy : Strategy {
    override fun findProcessToKill(processes: Iterable<ProcessData>, process: Process): Int? {
        return null
    }
}

object FifoStrategy : Strategy {
    override fun findProcessToKill(processes: Iterable<ProcessData>, process: Process): Int {
        val oldestProcess: ProcessData? = processes.minByOrNull { it.createdAt }
        return oldestProcess!!.process.pid
    }
}

object PriorityStrategy : Strategy {
    override fun findProcessToKill(processes: Iterable<ProcessData>, process: Process): Int? {

        val result = processes
            .filter { it.process.priority < process.priority }
            .minByOrNull { it.createdAt }

        return result?.process?.pid
    }
}