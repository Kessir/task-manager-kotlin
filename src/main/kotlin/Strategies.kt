import java.util.*


interface Strategy {
    fun findProcessToKill(processes: Iterable<ProcessData>, process: Process = Process(-1)): Int?
}

class FifoStrategy : Strategy {
    override fun findProcessToKill(processes: Iterable<ProcessData>, process: Process): Int {
        val oldestProcess: ProcessData? = processes.minByOrNull { it.createdAt }
        return oldestProcess!!.process.pid
    }
}

class PriorityStrategy : Strategy {
    override fun findProcessToKill(processes: Iterable<ProcessData>, process: Process): Int? {

        val result = processes
            .filter { it.process.priority < process.priority }
            .minByOrNull { it.createdAt }

        return result?.process?.pid
    }
}