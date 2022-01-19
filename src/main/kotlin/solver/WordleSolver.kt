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

        return sequence {
           do {
                val guesses = candidates.map { (_, words) ->
                    candidateSelector.selectBest(words)
                }
                yield(guesses)

                val (guess, result) = input.iterator().next()
                // TODO validate input?

                when {
                    else -> {
                        guessHistory.add(guess to result)

                        candidates = candidateFilters.associateWith { filter ->
                            filter.filterCandidates(fiveLetterWords, guessHistory)
                        }
                    }
                }
            } while (input.iterator().hasNext())
        }
    }
}