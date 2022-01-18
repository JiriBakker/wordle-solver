package util

fun String.wordleMatch(guess: String): String {
    val letterCounts = this.groupBy { it }.map { it.key to it.value.size }.toMap().toMutableMap()

    return (this.indices)
        .map { index ->
            val char = guess[index]
            if (char == this[index]) {
                letterCounts[char] = (letterCounts[char] ?: 1) - 1
                'x'
            } else {
                '-'
            }
        }.mapIndexed { index, intermediate ->
            val char = guess[index]
            when {
                intermediate == 'x' -> 'x'
                (letterCounts[char] ?: 0) > 0 -> {
                    letterCounts[char] = (letterCounts[char] ?: 1) - 1
                    '?'
                }
                else -> '-'
            }
        }.joinToString("")
}