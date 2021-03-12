fun main(args: Array<String>) {
    val taskManager = TaskManager(maxCapacity = 5)

    taskManager.addProcess(Process(1))
    taskManager.addProcess(Process(2))

    taskManager.killProcess(1)

    println(taskManager.listProcesses())
}