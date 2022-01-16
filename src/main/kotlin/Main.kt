import filters.RemainingCandidateFilter
import filters.RuleCandidateFilter
import java.io.File

private fun getFiveLetterWords() =
    File("words.txt").readLines()

private fun List<String>.findCandidateWithMostCoverage(): String? {
    val wordsPerLetter =
         ('a' .. 'z').associateWith { char ->
             this.count { it.contains(char) }
         }

    fun String.getUniqueLetters() =
        this.groupBy { it }.keys

    return this.maxByOrNull { word -> word.getUniqueLetters().sumOf { char -> wordsPerLetter[char]!! } }
}

fun main() {
    val fiveLetterWords = getFiveLetterWords()

    val candidateFilters = listOf(
        RuleCandidateFilter(),
        RemainingCandidateFilter()
    )

    val initialGuess = fiveLetterWords.findCandidateWithMostCoverage()

    println("Try: $initialGuess")

    val guessHistory = mutableListOf<Pair<String, String>>()

    while (true) {
        val guess = readLine()
        val result = readLine()

        if (result == "xxxxx") {
            return
        } else if (result?.length != 5) {
            println("Unexpected length ${result?.length}")
        } else {
            guessHistory.add(guess!! to result)

            val guesses = candidateFilters.map { strategy ->
                strategy
                    .filterCandidates(fiveLetterWords, guessHistory)
                    .findCandidateWithMostCoverage()
            }
            println("Try: ${guesses.joinToString(" or ")}")

        }
    }
}