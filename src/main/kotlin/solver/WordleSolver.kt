package solver

import filters.HybridCandidateFilter
import filters.RemainingCandidateFilter
import filters.RuleCandidateFilter
import input.FiveLetterWords
import selectors.SpreadCandidateSelector

object WordleSolver {

    fun suggestGuesses(input: Sequence<Pair<Guess, Result>>): Sequence<List<String?>> {
        val fiveLetterWords = FiveLetterWords.loadEnglish()

        val candidateFilters = listOf(
            RuleCandidateFilter(),
            RemainingCandidateFilter(),
            HybridCandidateFilter()
        )

        val candidateSelector = SpreadCandidateSelector()

        val guessHistory = mutableListOf<Pair<Guess, Result>>()
        var candidates = candidateFilters.associateWith { fiveLetterWords }

        val inputProvider = input.iterator()

        fun getGuesses() = candidates.map { (_, words) ->
            candidateSelector.selectBest(words)
        }

        return sequence {
            yield(getGuesses())

            do {
                val (guess, result) = inputProvider.next()
                // TODO validate input?

                guessHistory.add(guess to result)

                candidates = candidateFilters.associateWith { filter ->
                    filter.filterCandidates(fiveLetterWords, guessHistory)
                }

                yield(getGuesses())
            } while (inputProvider.hasNext())
        }
    }
}