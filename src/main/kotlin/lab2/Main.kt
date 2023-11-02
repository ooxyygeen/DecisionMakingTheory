package lab2

import kotlin.math.roundToInt

const val CAPACITY = 100

fun nextFitAlg(cargo: List<Int>, initialComplexity: Int = 0): Pair<Int, Int> {
    var containers = if (cargo.isEmpty()) 0 else 1
    var complexity = 0
    var currLoad = 0

    for (c in cargo) {
        if (CAPACITY - currLoad >= c) {
            currLoad += c
        } else {
            containers += 1
            currLoad = c
        }
        complexity += 1
    }

    return Pair(containers, complexity + initialComplexity)
}

fun firstFitAlg(cargo: List<Int>, initialComplexity: Int = 0): Pair<Int, Int> {
    val containers = mutableListOf(0)
    var complexity = 0

    for (c in cargo) {
        if (CAPACITY - containers[containers.size - 1] >= c) {
            containers[containers.size - 1] += c
        } else {
            var isInserted = false

            for (i in containers.size - 2 downTo 0) {
                complexity += 1
                if (CAPACITY - containers[i] >= c) {
                    containers[i] += c
                    isInserted = true
                    break
                }
            }

            if (!isInserted) {
                containers.add(c)
            }
        }
        complexity += 1
    }

    return Pair(containers.size, complexity + initialComplexity)
}

fun worstFitAlg(cargo: List<Int>, initialComplexity: Int = 0): Pair<Int, Int> {
    val containers = mutableListOf(0)
    var complexity = 0

    for (c in cargo) {
        if (CAPACITY - containers[containers.size - 1] >= c) {
            containers[containers.size - 1] += c
        } else {
            val min = containers.indexOf(containers.min())
            complexity += containers.size
            if (CAPACITY - containers[min] >= c) {
                containers[min] += c
            } else {
                containers.add(c)
            }
            complexity += 1
        }
        complexity += 1
    }

    return Pair(containers.size, complexity + initialComplexity)
}

fun bestFitAlg(cargo: List<Int>, initialComplexity: Int = 0): Pair<Int, Int> {
    val containers = mutableListOf(0)
    var complexity = 0

    for (c in cargo) {
        if (CAPACITY - containers[containers.size - 1] >= c) {
            containers[containers.size - 1] += c
        } else {
            val min = containers.filter { CAPACITY - it >= c}.minOrNull()
            val idx = if (min != null) containers.indexOf(min) else -1
            complexity += containers.size
            if (idx > -1) {
                containers[idx] += c
            } else {
                containers.add(c)
            }
            complexity += 1
        }
        complexity += 1
    }

    return Pair(containers.size, complexity + initialComplexity)
}

fun calculate(name: String, row: List<Int>) {
    var list = row
    val sortedSizesComplexity = (row.size * (Math.log(row.size.toDouble()) / Math.log(2.0))).roundToInt()

    println(name)
    println("Без впорядкування")
    var nfa = nextFitAlg(list)
    println("NFA | контейнерів: ${nfa.first} --- складність:${nfa.second}")
    var ffa = firstFitAlg(list)
    println("FFA | контейнерів: ${ffa.first} --- складність:${ffa.second}")
    var wfa = worstFitAlg(list)
    println("WFA | контейнерів: ${wfa.first} --- складність:${wfa.second}")
    var bfa = bestFitAlg(list)
    println("BFA | контейнерів: ${bfa.first} --- складність:${bfa.second}")

    println("З впорядкуванням за зростанням")
    list = row.sorted()
    nfa = nextFitAlg(list, sortedSizesComplexity)
    println("NFA | контейнерів: ${nfa.first} --- складність:${nfa.second}")
    ffa = firstFitAlg(list, sortedSizesComplexity)
    println("FFA | контейнерів: ${ffa.first} --- складність:${ffa.second}")
    wfa = worstFitAlg(list, sortedSizesComplexity)
    println("WFA | контейнерів: ${wfa.first} --- складність:${wfa.second}")
    bfa = bestFitAlg(list, sortedSizesComplexity)
    println("BFA | контейнерів: ${bfa.first} --- складність:${bfa.second}")

    println("З впорядкуванням за спаданням")
    list = row.sortedDescending()
    nfa = nextFitAlg(list, sortedSizesComplexity)
    println("NFA | контейнерів: ${nfa.first} --- складність:${nfa.second}")
    ffa = firstFitAlg(list, sortedSizesComplexity)
    println("FFA | контейнерів: ${ffa.first} --- складність:${ffa.second}")
    wfa = worstFitAlg(list, sortedSizesComplexity)
    println("WFA | контейнерів: ${wfa.first} --- складність:${wfa.second}")
    bfa = bestFitAlg(list, sortedSizesComplexity)
    println("BFA | контейнерів: ${bfa.first} --- складність:${bfa.second}")
}

fun main() {
    val row1 = listOf(63, 55, 90, 58, 30, 4, 71, 61, 33, 85, 89, 73, 4, 51, 5, 50, 68, 3, 85, 6)
    val row2 = listOf(95, 39, 49, 20, 67, 26, 63, 77, 96, 81, 65, 60, 36, 55, 70, 18, 11, 42, 32, 96)
    val row3 = listOf(79, 21, 70, 84, 72, 27, 34, 40, 83, 72, 98, 30, 63, 47, 50, 30, 73, 14, 59, 22)
    val total = listOf(row1, row2, row3).flatten()
    val rows = mapOf("Ряд 1" to row1,
                     "Ряд 2" to row2,
                     "Ряд 3" to row3,
                     "Разом" to total)

    for (r in rows) {
        calculate(r.key, r.value)
    }
}