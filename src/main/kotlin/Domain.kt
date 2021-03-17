import java.util.*

enum class ProcessPriority {
    LOW , MEDIUM, HIGH
}

enum class SortOptions {
    CREATED_AT, PRIORITY, PID
}


data class Process(val pid: Int, val priority: ProcessPriority = ProcessPriority.LOW) {
    fun kill(){}
}

class ProcessData(val process: Process, val createdAt: Date)
