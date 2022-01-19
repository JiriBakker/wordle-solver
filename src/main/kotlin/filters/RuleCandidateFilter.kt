package filters

import solver.Guess
import solver.Result

class RuleCandidateFilter : CandidateFilter {

    private fun Pair<Guess, Result>.countMatches(letter: Char) =
        this.first.withIndex().count { it.value == letter && this.second[it.index] != '-' }

    override fun filterCandidates(
        candidates: List<String>,
        guessHistory: List<Pair<Guess, Result>>
    ): List<String> {
        val rules = mutableListOf<(String) -> Boolean>()

        guessHistory.forEach { (guess, result) ->
            result.forEachIndexed { index, char ->
                val guessedLetter = guess[index]
                when (char) {
                    '-' -> {
                        val matchCount = (guess to result).countMatches(guessedLetter)
                        rules.add { word -> word.count { it == guessedLetter } == matchCount }
                    }
                    '?' -> rules.add { word -> word.any { it == guessedLetter } && word[index] != guessedLetter }
                    'x' -> rules.add { word -> word[index] == guessedLetter }
                }
            }
        }

        return candidates.filter { word -> rules.all { rule -> rule(word) } }
    }
}