package lab1

fun main(args: Array<String>) {
    val row1 = listOf(63, 55, 90, 58, 30, 4, 71, 61, 33, 85, 89, 73, 4, 51, 5, 50, 68, 3, 85, 6)
    val row2 = listOf(95, 39, 49, 20, 67, 26, 63, 77, 96, 81, 65, 60, 36, 55, 70, 18, 11, 42, 32, 96)
    val row3 = listOf(79, 21, 70, 84, 72, 27, 34, 40, 83, 72, 98, 30, 63, 47, 50, 30, 73, 14, 59, 22)
    val ira = listOf(72, 22, 54, 35, 21, 57, 65, 47, 71, 76, 69, 18, 1, 3, 53, 33, 7, 59, 28, 6)
    val numbers = listOf(ira).flatten()

    var optimals = find(numbers, ::compareByPareto)
    println("Множина оптимальних значень за Парето:")
    for (o in optimals) {
        val index = findIndexesOfElement(numbers, o)
        for (i in index) {
            print("A${i+1} = $o; ")
        }
    }

    optimals = find(numbers, ::compareBySlater);
    println("\n\nМножина оптимальних значень за Слейтером:")
    for (o in optimals) {
        val index = findIndexesOfElement(numbers, o)
        for (i in index) {
            print("A${i+1} = $o; ")
        }
    }
}

fun findIndexesOfElement(list: List<Int>, targetElement: Int): List<Int> {
    return list.withIndex().filter { it.value == targetElement }.map { it.index }
}

fun compareByPareto(i: Int, j: Int): Boolean {
    return (i / 10 >= j / 10) && (i % 10 >= j % 10)
}

fun compareBySlater(i: Int, j: Int): Boolean {
    return (i / 10 > j / 10) && (i % 10 > j % 10)
}

fun find(aList: List<Int>, comparator: (Int, Int) -> Boolean): Set<Int> {
    val list = aList.toMutableList()
    var i = 0
    var j = 1

    while (i < list.size - 1) {
        if (comparator(list[i], list[j])) {
            list.removeAt(j)
            if (j >= list.size) {
                i += 1
                j = i + 1
            }
            continue
        } else if (comparator(list[j], list[i])) {
            list.removeAt(i)
            j = i + 1
            continue
        }
        if (j < list.size - 1) {
            j += 1
        } else {
            i += 1
            j = i + 1
        }
    }

    return list.toSet()
}
