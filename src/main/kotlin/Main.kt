import filters.DynamicCandidateFilter
import filters.RemainingCandidateFilter
import filters.RuleCandidateFilter
import input.FiveLetterWords
import selectors.SpreadCandidateSelector

fun main() {
    val fiveLetterWords = FiveLetterWords.load()

    val candidateFilters = listOf(
        RuleCandidateFilter(),
        RemainingCandidateFilter(),
        DynamicCandidateFilter(100)
    )

    val candidateSelector = SpreadCandidateSelector()

    val guessHistory = mutableListOf<Pair<String, String>>()
    var candidates = candidateFilters.associateWith { fiveLetterWords }

    while (true) {
        val guesses = candidates.map { (_, words) ->
            candidateSelector.selectBest(words)
        }
        println("Try: ${guesses.joinToString(" - ")}")

        val guess  = readLine()
        val result = readLine()

        when {
            guess?.length  != 5 -> println("Unexpected length ${guess?.length}. Try again")
            result?.length != 5 -> println("Unexpected length ${result?.length}. Try again")
            else -> {
                guessHistory.add(guess to result)

                candidates = candidateFilters.associateWith { filter ->
                    filter.filterCandidates(fiveLetterWords, guessHistory)
                }
            }
        }
    }
}